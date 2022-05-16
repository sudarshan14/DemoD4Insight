package sud.bhatt.wml

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadLocalToCloud(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        return  Result.success()
    }
}