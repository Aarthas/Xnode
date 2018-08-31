/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fine.xnode.livedata;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.Iterator;
import java.util.Map;

public abstract class LiveData<T> {
    private final Object mDataLock = new Object();
    static final int START_VERSION = -1;
    private static final Object NOT_SET = new Object();

    private SafeIterableMap<Observer<T>, ObserverWrapper> mObservers =
            new SafeIterableMap<>();

    // how many observers are in active state
    private int mActiveCount = 0;
    private volatile Object mData = NOT_SET;
    // when setData is called, we set the pending data and actual data swap happens on the main
    // thread
    private volatile Object mPendingData = NOT_SET;
    private int mVersion = START_VERSION;

    private boolean mDispatchingValue;
    @SuppressWarnings("FieldCanBeLocal")
    private boolean mDispatchInvalidated;
    private final Runnable mPostValueRunnable = new Runnable() {
        @Override
        public void run() {
            Object newValue;
            synchronized (mDataLock) {
                newValue = mPendingData;
                mPendingData = NOT_SET;
            }
            //noinspection unchecked
            setValue((T) newValue);
        }
    };

    private void considerNotify(ObserverWrapper observer) {
        if (!observer.mActive) {
            return;
        }
        // Check latest state b4 dispatch. Maybe it changed state but we didn't get the event yet.
        //
        // we still first check observer.active to keep it as the entrance for events. So even if
        // the observer moved to an active state, if we've not received that event, we better not
        // notify for a more predictable notification order.
        if (!observer.shouldBeActive()) {
            observer.activeStateChanged(false);
            return;
        }
        if (observer.mLastVersion >= mVersion) {
            return;
        }
        observer.mLastVersion = mVersion;
        //noinspection unchecked
        observer.mObserver.onChanged((T) mData);
    }

    private void dispatchingValue(@Nullable ObserverWrapper initiator) {
        if (mDispatchingValue) {
            mDispatchInvalidated = true;
            return;
        }
        mDispatchingValue = true;
        do {
            mDispatchInvalidated = false;
            if (initiator != null) {
                considerNotify(initiator);
                initiator = null;
            } else {
                for (Iterator<Map.Entry<Observer<T>, ObserverWrapper>> iterator =
                     mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
                    considerNotify(iterator.next().getValue());
                    if (mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (mDispatchInvalidated);
        mDispatchingValue = false;
    }

    @MainThread
    public void observeForever(@NonNull Observer<T> observer) {
        AlwaysActiveObserver wrapper = new AlwaysActiveObserver(observer);
        ObserverWrapper existing = mObservers.putIfAbsent(observer, wrapper);
        if (existing != null) {
            throw new IllegalArgumentException("Cannot add the same observer"
                    + " with different lifecycles");
        }
        if (existing != null) {
            return;
        }
        wrapper.activeStateChanged(true);
    }

    /**
     * Removes the given observer from the observers list.
     *
     * @param observer The Observer to receive events.
     */
    @MainThread
    public void removeObserver(@NonNull final Observer<T> observer) {
        ObserverWrapper removed = mObservers.remove(observer);
        if (removed == null) {
            return;
        }
        removed.detachObserver();
        removed.activeStateChanged(false);
    }

    /**
     * Removes all observers that are tied to the given {@link LifecycleOwner}.
     *
     * @param owner The {@code LifecycleOwner} scope for the observers to be removed.
     */

    /**
     * Posts a task to a main thread to set the given value. So if you have a following code
     * executed in the main thread:
     * <pre class="prettyprint">
     * liveData.postValue("a");
     * liveData.setValue("b");
     * </pre>
     * The value "b" would be set at first and later the main thread would override it with
     * the value "a".
     * <p>
     * If you called this method multiple times before a main thread executed a posted task, only
     * the last value would be dispatched.
     *
     * @param value The new value
     */
//    protected void postValue(T value) {
//        boolean postTask;
//        synchronized (mDataLock) {
//            postTask = mPendingData == NOT_SET;
//            mPendingData = value;
//        }
//        if (!postTask) {
//            return;
//        }
//        ArchTaskExecutor.getInstance().postToMainThread(mPostValueRunnable);
//    }
    @MainThread
    protected void setValue(T value) {
        mVersion++;
        mData = value;
        dispatchingValue(null);
    }

    @Nullable
    public T getValue() {
        Object data = mData;
        if (data != NOT_SET) {
            //noinspection unchecked
            return (T) data;
        }
        return null;
    }

    int getVersion() {
        return mVersion;
    }

    protected void onActive() {

    }

    protected void onInactive() {

    }

    /**
     * Returns true if this LiveData has observers.
     *
     * @return true if this LiveData has observers
     */
    @SuppressWarnings("WeakerAccess")
    public boolean hasObservers() {
        return mObservers.size() > 0;
    }

    /**
     * Returns true if this LiveData has active observers.
     *
     * @return true if this LiveData has active observers
     */
    @SuppressWarnings("WeakerAccess")
    public boolean hasActiveObservers() {
        return mActiveCount > 0;
    }

    public void justSetValue(T value) {
        mVersion++;
        mData = value;
    }


    private abstract class ObserverWrapper {
        final Observer<T> mObserver;
        boolean mActive;
        int mLastVersion = START_VERSION;

        ObserverWrapper(Observer<T> observer) {
            mObserver = observer;
        }

        abstract boolean shouldBeActive();


        void detachObserver() {
        }

        void activeStateChanged(boolean newActive) {
            if (newActive == mActive) {
                return;
            }
            // immediately set active state, so we'd never dispatch anything to inactive
            // owner
            mActive = newActive;
            boolean wasInactive = LiveData.this.mActiveCount == 0;
            LiveData.this.mActiveCount += mActive ? 1 : -1;
            if (wasInactive && mActive) {
                onActive();
            }
            if (LiveData.this.mActiveCount == 0 && !mActive) {
                onInactive();
            }
            if (mActive) {
                dispatchingValue(this);
            }
        }
    }

    private class AlwaysActiveObserver extends ObserverWrapper {

        AlwaysActiveObserver(Observer<T> observer) {
            super(observer);
        }

        @Override
        boolean shouldBeActive() {
            return true;
        }
    }

}
