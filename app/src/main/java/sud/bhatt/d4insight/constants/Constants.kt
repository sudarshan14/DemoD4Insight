package sud.bhatt.d4insight.constants

class Constants {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"

        //api key can be saved on cloud/firebase remote config and get on app launch
        const val API_KEY = "251b26f66cb770732d0ce1e863707fa6"

        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVED = 2



        // Name of Notification Channel for verbose notifications of background work
        @JvmField val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "Verbose WorkManager Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        @JvmField val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"
        const val TAG_OUTPUT = "OUTPUT"
        const val OUTPUT_PATH = "blur_filter_outputs"
        const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
        const val DELAY_TIME_MILLIS: Long = 3000

    }
}