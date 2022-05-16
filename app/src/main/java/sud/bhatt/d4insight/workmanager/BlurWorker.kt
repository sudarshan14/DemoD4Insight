package sud.bhatt.d4insight.workmanager

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay
import sud.bhatt.d4insight.constants.Constants.Companion.KEY_IMAGE_URI
import sud.bhatt.d4insight.constants.Constants.Companion.OUTPUT_PATH
import sud.bhatt.d4insight.utils.*
import java.io.File

class BlurWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {

        val appContext = applicationContext

        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        makeStatusNotification("Blurring Image", appContext)

        return try {

            if (TextUtils.isEmpty(resourceUri)) {
                throw IllegalArgumentException("Invalid input")
            }
            val resolver = appContext.contentResolver

            delay(4000)

//            val picture = BitmapFactory.decodeResource(appContext.resources, R.drawable.sudarshan)

            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )
            val output = blurBitmap(picture, appContext)
//
            val outputUri = writeBitmapToFile(appContext, output)

            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            makeStatusNotification("Output is $outputUri", appContext)


            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }

    }

//    private fun makeStatusNotification(s: String, appContext: Context) {
//        debugLogger(UNI_TAG, s)
//    }
}