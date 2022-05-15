package sud.bhatt.d4insight.view.blur

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager

class BlurViewModelFactory constructor(private val workManager: WorkManager, private val application: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
            BlurViewModel(this.workManager, this.application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}