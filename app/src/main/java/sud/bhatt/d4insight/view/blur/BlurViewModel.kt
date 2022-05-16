package sud.bhatt.d4insight.view.blur

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import sud.bhatt.d4insight.R
import sud.bhatt.d4insight.constants.Constants.Companion.IMAGE_MANIPULATION_WORK_NAME
import sud.bhatt.d4insight.constants.Constants.Companion.KEY_IMAGE_URI
import sud.bhatt.d4insight.constants.Constants.Companion.TAG_OUTPUT
import sud.bhatt.d4insight.workmanager.BlurWorker
import sud.bhatt.d4insight.workmanager.worker.CleanupWorker

class BlurViewModel(private val workManager: WorkManager, application: Context) : ViewModel() {

    private var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    internal val status: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)

//   internal var status: LiveData<WorkInfo> =
//        workManager.getWorkInfoByIdLiveData(workA.id)

    init {
        imageUri = getImageUri(application)
    }

    internal fun applyBlur(blurLevel: Int) {


        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )
        continuation.enqueue()


        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
                .addTag(TAG_OUTPUT)

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }
            continuation = continuation.then(blurBuilder.build())
        }

        // Actually start the work
        continuation.enqueue()
//        for (i in 0 until blurLevel) {
//
//            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
//                .setInputData(createInputDataForUri(i))
//                .addTag(TAG_OUTPUT)
//                .build()
//
//            workManager.enqueue(blurBuilder)
//
//        }


//        workManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))
    }


    internal fun cancelWork() {
        workManager.cancelAllWorkByTag(TAG_OUTPUT)
//        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun createInputDataForUri(): Data {

        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()

    }


    private fun getImageUri(context: Context): Uri {
        val resources = context.resources

        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.sudarshan))
            .appendPath(resources.getResourceTypeName(R.drawable.sudarshan))
            .appendPath(resources.getResourceEntryName(R.drawable.sudarshan))
            .build()
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

}