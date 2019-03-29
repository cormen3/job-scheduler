package com.example.jobscheduler.job

import android.app.Service
import android.content.Intent
import android.util.Log
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.*
import android.support.annotation.RequiresApi
import com.example.jobscheduler.MainActivity.Companion.MESSENGER_INTENT_KEY
import com.example.jobscheduler.MainActivity.Companion.MSG_COLOR_START
import com.example.jobscheduler.MainActivity.Companion.MSG_COLOR_STOP
import com.example.jobscheduler.MainActivity.Companion.WORK_DURATION_KEY


/**
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method. It runs jobs for a specific amount of time
 * and finishes them. It keeps the activity updated with changes via a Messenger.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {

    private var mActivityMessenger: Messenger? = null

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service destroyed")
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY)
        return Service.START_NOT_STICKY
    }

    override fun onStartJob(params: JobParameters): Boolean {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).

        Log.i("testLogScheduler", "is start...")

        sendMessage(MSG_COLOR_START, params.jobId)

        val duration = params.extras.getLong(WORK_DURATION_KEY)

        // Uses a handler to delay the execution of jobFinished().
        val handler = Handler()
        handler.postDelayed({
            sendMessage(MSG_COLOR_STOP, params.jobId)
            jobFinished(params, false)
        }, duration)
        Log.i(TAG, "on start job: " + params.jobId)

        // Return true as there's more work to be done with this job.
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        // Stop tracking these job parameters, as we've 'finished' executing.
        Log.i("testLogScheduler", "STOP")
        sendMessage(MSG_COLOR_STOP, params.jobId)
        Log.i(TAG, "on stop job: " + params.jobId)

        // Return false to drop the job.
        return false
    }

    private fun sendMessage(messageID: Int, params: Any?) {
        // If this service is launched by the JobScheduler, there's no callback Messenger. It
        // only exists when the MainActivity calls startService() with the callback in the Intent.
        if (mActivityMessenger == null) {
            Log.d(TAG, "Service is bound, not started. There's no callback to send a message to.")
            return
        }
        val m = Message.obtain()
        m.what = messageID
        m.obj = params
        try {
            mActivityMessenger!!.send(m)
        } catch (e: RemoteException) {
            Log.e(TAG, "Error passing service object back to activity.")
        }

    }

    companion object {
        private val TAG = MyJobService::class.java.simpleName
    }
}
