package ng.com.techdepo.kotlincodelabs


import android.os.Build
import androidx.work.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ng.com.techdepo.kotlincodelabs.di.DaggerAppComponent
import ng.com.techdepo.kotlincodelabs.work.RefreshDataWorker
import java.util.concurrent.TimeUnit

class MarsApp : DaggerApplication() {


    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() {
        applicationScope.launch {

            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }
                .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1,
                TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}