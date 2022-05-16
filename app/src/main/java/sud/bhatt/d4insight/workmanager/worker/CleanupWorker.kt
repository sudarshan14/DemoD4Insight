package sud.bhatt.d4insight.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import sud.bhatt.d4insight.constants.Constants.Companion.OUTPUT_PATH
import sud.bhatt.d4insight.utils.UNI_TAG
import sud.bhatt.d4insight.utils.debugLogger
import sud.bhatt.d4insight.utils.makeStatusNotification
import java.io.File

class CleanupWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        // Makes a notification when the work starts and slows down the work so that
        // it's easier to see each WorkRequest start, even on emulated devices
        makeStatusNotification("Cleaning up old temporary files", applicationContext)


        return try {
            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null) {
                    for (entry in entries) {
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            debugLogger(UNI_TAG, "deleted $name -$deleted")
                            makeStatusNotification("Cleaning old images", applicationContext)
                        }
                    }
                }
            }
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }
}