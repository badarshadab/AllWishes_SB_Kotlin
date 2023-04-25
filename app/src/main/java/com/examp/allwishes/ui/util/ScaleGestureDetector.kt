package com.examp.allwishes.ui.util

import android.util.Log
import android.view.MotionEvent
import android.view.View

class ScaleGestureDetector(listener: OnScaleGestureListener?) {

    private val TAG = "ScaleGestureDetector"
    private val PRESSURE_THRESHOLD = 0.67f
    private var mListener: OnScaleGestureListener? = null
    private var mGestureInProgress = false
    private var mPrevEvent: MotionEvent? = null
    private var mCurrEvent: MotionEvent? = null
    private var mCurrSpanVector: Vector2D? = null
    private var mFocusX = 0f
    private var mFocusY = 0f
    private var mPrevFingerDiffX = 0f
    private var mPrevFingerDiffY = 0f
    private var mCurrFingerDiffX = 0f
    private var mCurrFingerDiffY = 0f
    private var mCurrLen = 0f
    private var mPrevLen = 0f
    private var mScaleFactor = 0f
    private var mCurrPressure = 0f
    private var mPrevPressure = 0f
    private var mTimeDelta: Long = 0
    private var mInvalidGesture = false
    private var mActiveId0 = 0
    private var mActiveId1 = 0
    private var mActive0MostRecent = false

    fun ScaleGestureDetector(listener: OnScaleGestureListener?) {
        mListener = listener
        mCurrSpanVector = Vector2D()
    }

    fun onTouchEvent(view: View, event: MotionEvent): Boolean {
        val action = event.actionMasked
        if (action == 0) {
            reset()
        }
        var handled = true
        if (mInvalidGesture) {
            handled = false
        } else {
            val index1: Int
            var actionIndex: Int
            if (!mGestureInProgress) {
                when (action) {
                    0 -> {
                        mActiveId0 = event.getPointerId(0)
                        mActive0MostRecent = true
                    }
                    1 -> reset()
                    2, 3, 4 -> {}
                    5 -> {
                        if (mPrevEvent != null) {
                            mPrevEvent?.recycle()
                        }
                        mPrevEvent = MotionEvent.obtain(event)
                        mTimeDelta = 0L
                        index1 = event.actionIndex
                        actionIndex = event.findPointerIndex(mActiveId0)
                        mActiveId1 = event.getPointerId(index1)
                        if (actionIndex < 0 || actionIndex == index1) {
                            actionIndex = findNewActiveIndex(event, mActiveId1, -1)
                            mActiveId0 = event.getPointerId(actionIndex)
                        }
                        mActive0MostRecent = false
                        setContext(view, event)
                        mGestureInProgress = mListener!!.onScaleBegin(view, this)
                    }
                    else -> {}
                }
            } else {
                var actionId: Int
                when (action) {
                    1 -> reset()
                    2 -> {
                        setContext(view, event)
                        if (mCurrPressure / mPrevPressure > 0.67f) {
                            val updatePrevious: Boolean = mListener!!.onScale(view, this)
                            if (updatePrevious) {
                                mPrevEvent!!.recycle()
                                mPrevEvent = MotionEvent.obtain(event)
                            }
                        }
                    }
                    3 -> {
                        mListener?.onScaleEnd(view, this)
                        reset()
                    }
                    4 -> {}
                    5 -> {
                        mListener!!.onScaleEnd(view, this)
                        index1 = mActiveId0
                        actionIndex = mActiveId1
                        reset()
                        mPrevEvent = MotionEvent.obtain(event)
                        mActiveId0 = if (mActive0MostRecent) index1 else actionIndex
                        mActiveId1 = event.getPointerId(event.actionIndex)
                        mActive0MostRecent = false
                        actionId = event.findPointerIndex(mActiveId0)
                        if (actionId < 0 || mActiveId0 == mActiveId1) {
                            actionId = findNewActiveIndex(event, mActiveId1, -1)
                            mActiveId0 = event.getPointerId(actionId)
                        }
                        setContext(view, event)
                        mGestureInProgress = mListener!!.onScaleBegin(view, this)
                    }
                    6 -> {
                        index1 = event.pointerCount
                        actionIndex = event.actionIndex
                        actionId = event.getPointerId(actionIndex)
                        var gestureEnded = false
                        var newIndex: Int
                        if (index1 > 2) {
                            if (actionId == mActiveId0) {
                                newIndex = findNewActiveIndex(event, mActiveId1, actionIndex)
                                if (newIndex >= 0) {
                                    mListener!!.onScaleEnd(view, this)
                                    mActiveId0 = event.getPointerId(newIndex)
                                    mActive0MostRecent = true
                                    mPrevEvent = MotionEvent.obtain(event)
                                    setContext(view, event)
                                    mGestureInProgress = mListener!!.onScaleBegin(view, this)
                                } else {
                                    gestureEnded = true
                                }
                            } else if (actionId == mActiveId1) {
                                newIndex = findNewActiveIndex(event, mActiveId0, actionIndex)
                                if (newIndex >= 0) {
                                    mListener!!.onScaleEnd(view, this)
                                    mActiveId1 = event.getPointerId(newIndex)
                                    mActive0MostRecent = false
                                    mPrevEvent = MotionEvent.obtain(event)
                                    setContext(view, event)
                                    mGestureInProgress = mListener!!.onScaleBegin(view, this)
                                } else {
                                    gestureEnded = true
                                }
                            }
                            mPrevEvent!!.recycle()
                            mPrevEvent = MotionEvent.obtain(event)
                            setContext(view, event)
                        } else {
                            gestureEnded = true
                        }
                        if (gestureEnded) {
                            setContext(view, event)
                            newIndex = if (actionId == mActiveId0) mActiveId1 else mActiveId0
                            val index = event.findPointerIndex(newIndex)
                            mFocusX = event.getX(index)
                            mFocusY = event.getY(index)
                            mListener!!.onScaleEnd(view, this)
                            reset()
                            mActiveId0 = newIndex
                            mActive0MostRecent = true
                        }
                    }
                    else -> {}
                }
            }
        }
        return handled
    }

    private fun findNewActiveIndex(
        ev: MotionEvent,
        otherActiveId: Int,
        removedPointerIndex: Int
    ): Int {
        val pointerCount = ev.pointerCount
        val otherActiveIndex = ev.findPointerIndex(otherActiveId)
        for (i in 0 until pointerCount) {
            if (i != removedPointerIndex && i != otherActiveIndex) {
                return i
            }
        }
        return -1
    }

    private fun setContext(view: View, curr: MotionEvent) {
        if (mCurrEvent != null) {
            mCurrEvent!!.recycle()
        }
        mCurrEvent = MotionEvent.obtain(curr)
        mCurrLen = -1.0f
        mPrevLen = -1.0f
        mScaleFactor = -1.0f
        mCurrSpanVector!![0.0f] = 0.0f
        val prev = mPrevEvent
        val prevIndex0 = prev!!.findPointerIndex(mActiveId0)
        val prevIndex1 = prev.findPointerIndex(mActiveId1)
        val currIndex0 = curr.findPointerIndex(mActiveId0)
        val currIndex1 = curr.findPointerIndex(mActiveId1)
        if (prevIndex0 >= 0 && prevIndex1 >= 0 && currIndex0 >= 0 && currIndex1 >= 0) {
            val px0 = prev.getX(prevIndex0)
            val py0 = prev.getY(prevIndex0)
            val px1 = prev.getX(prevIndex1)
            val py1 = prev.getY(prevIndex1)
            val cx0 = curr.getX(currIndex0)
            val cy0 = curr.getY(currIndex0)
            val cx1 = curr.getX(currIndex1)
            val cy1 = curr.getY(currIndex1)
            val pvx = px1 - px0
            val pvy = py1 - py0
            val cvx = cx1 - cx0
            val cvy = cy1 - cy0
            mCurrSpanVector!![cvx] = cvy
            mPrevFingerDiffX = pvx
            mPrevFingerDiffY = pvy
            mCurrFingerDiffX = cvx
            mCurrFingerDiffY = cvy
            mFocusX = cx0 + cvx * 0.5f
            mFocusY = cy0 + cvy * 0.5f
            mTimeDelta = curr.eventTime - prev.eventTime
            mCurrPressure = curr.getPressure(currIndex0) + curr.getPressure(currIndex1)
            mPrevPressure = prev.getPressure(prevIndex0) + prev.getPressure(prevIndex1)
        } else {
            mInvalidGesture = true
            Log.e("ScaleGestureDetector", "Invalid MotionEvent stream detected.", Throwable())
            if (mGestureInProgress) {
                mListener!!.onScaleEnd(view, this)
            }
        }
    }

    private fun reset() {
        if (mPrevEvent != null) {
            mPrevEvent!!.recycle()
            mPrevEvent = null
        }
        if (mCurrEvent != null) {
            mCurrEvent!!.recycle()
            mCurrEvent = null
        }
        mGestureInProgress = false
        mActiveId0 = -1
        mActiveId1 = -1
        mInvalidGesture = false
    }

    fun isInProgress(): Boolean {
        return mGestureInProgress
    }

    fun getFocusX(): Float {
        return mFocusX
    }

    fun getFocusY(): Float {
        return mFocusY
    }

    fun getCurrentSpan(): Float {
        if (mCurrLen == -1.0f) {
            val cvx = mCurrFingerDiffX
            val cvy = mCurrFingerDiffY
            mCurrLen = Math.sqrt((cvx * cvx + cvy * cvy).toDouble()).toFloat()
        }
        return mCurrLen
    }

    fun getCurrentSpanVector(): Vector2D? {
        return mCurrSpanVector
    }

    fun getCurrentSpanX(): Float {
        return mCurrFingerDiffX
    }

    fun getCurrentSpanY(): Float {
        return mCurrFingerDiffY
    }

    fun getPreviousSpan(): Float {
        if (mPrevLen == -1.0f) {
            val pvx = mPrevFingerDiffX
            val pvy = mPrevFingerDiffY
            mPrevLen = Math.sqrt((pvx * pvx + pvy * pvy).toDouble()).toFloat()
        }
        return mPrevLen
    }

    fun getPreviousSpanX(): Float {
        return mPrevFingerDiffX
    }

    fun getPreviousSpanY(): Float {
        return mPrevFingerDiffY
    }

    fun getScaleFactor(): Float {
        if (mScaleFactor == -1.0f) {
            mScaleFactor = getCurrentSpan() / getPreviousSpan()
        }
        return mScaleFactor
    }

    fun getTimeDelta(): Long {
        return mTimeDelta
    }

    fun getEventTime(): Long {
        return mCurrEvent!!.eventTime
    }

    interface OnScaleGestureListener {
        fun onScale(
            var1: View?,
            var2: ScaleGestureDetector?
        ): Boolean

        fun onScaleBegin(
            var1: View?,
            var2: ScaleGestureDetector?
        ): Boolean

        fun onScaleEnd(var1: View?, var2: ScaleGestureDetector?)
    }

    open class SimpleOnScaleGestureListener : OnScaleGestureListener {
        override fun onScale(
            view: View?,
            detector: ScaleGestureDetector?
        ): Boolean {
            return false
        }

        override fun onScaleBegin(
            view: View?,
            detector: ScaleGestureDetector?
        ): Boolean {
            return true
        }

        override fun onScaleEnd(
            view: View?,
            detector: ScaleGestureDetector?
        ) {
        }
    }
}