package me.uptop.uikit

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView

class ProgressDialog(context: Context) : Dialog(context) {
    private val mainHandler = Handler(Looper.getMainLooper())

    private var startTime: Long = NOT_INITIALIZED_TIME
    private var postedHide = false
    private var postedShow = false
    private var dismissed = false
    private var isShowNow = false

//    private val tv by lazy { findViewById<TextView>(R.id.tvLoadingDescription) }

    private val delayedHide = Runnable {
        postedHide = false
        startTime = NOT_INITIALIZED_TIME
        maybeDismiss()
    }

    private val delayedShow = Runnable {
        postedShow = false
        if (!dismissed) {
            startTime = System.currentTimeMillis()
            maybeSuperShow()
        }
    }

    init {
//        setContentView(R.layout.dialog_progress)
        setCancelable(false)
    }

    fun setShowing(isShowing: Boolean) {
        if (isShowing) {
            if (!super.isShowing()) {
                maybeShow()
            }
        } else {
            dismiss()
        }
    }

    fun showNow() {
        isShowNow = true
        maybeShow()
    }

    override fun show() {
        startTime = NOT_INITIALIZED_TIME
        dismissed = false
        mainHandler.removeCallbacks(delayedHide)
        postedHide = false
        if (!postedShow) {
            if (isShowNow) {
                mainHandler.post(delayedShow)
            } else {
                mainHandler.postDelayed(delayedShow, MIN_DELAY_MS)
            }

            postedShow = true
            isShowNow = false
        }
    }

    override fun dismiss() {
        dismissed = true
        mainHandler.removeCallbacks(delayedShow)
        postedShow = false
        val diff = System.currentTimeMillis() - startTime
        if (diff >= MIN_SHOW_TIME_MS || startTime == NOT_INITIALIZED_TIME) {
            maybeDismiss()
        } else {
            if (!postedHide) {
                mainHandler.postDelayed(delayedHide, MIN_SHOW_TIME_MS - diff)
                postedHide = true
            }
        }
    }

    fun dismissNow() {
        mainHandler.removeCallbacks(delayedShow)
        dismissed = true
        postedShow = false
        postedHide = false
        maybeDismiss()
    }

    private fun maybeDismiss() {
        ignoreError {
            if (isShowing) {
                super.dismiss()
            }
        }
    }

    private fun maybeSuperShow() {
        ignoreError {
            super.show()
        }
    }

    private fun maybeShow() {
        ignoreError {
            show()
        }
    }

    private fun ignoreError(block: () -> Unit) {
        try {
            block.invoke()
        } catch (t: Throwable) {
            Log.e(ProgressDialog::class.java.simpleName, t.message)
        }
    }

    companion object {
        private const val NOT_INITIALIZED_TIME = -1L
        private const val MIN_SHOW_TIME_MS = 500L
        private const val MIN_DELAY_MS = 250L
    }
}