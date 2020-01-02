package ng.com.techdepo.kotlincodelabs.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ng.com.techdepo.kotlincodelabs.network.MarsApiFilter
import ng.com.techdepo.kotlincodelabs.repository.MarsRepository
import retrofit2.HttpException

class RefreshDataWorker(context: Context,params: WorkerParameters):CoroutineWorker(context,params) {

    companion object {
        const val WORK_NAME = "ng.com.techdepo.kotlincodelabs.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {

//        val database = getDatabase(applicationContext)
//        val repository = MarsRepository(database)
//
//        try {
//
//            repository.refreshMars(MarsApiFilter.SHOW_ALL.value)
//        }catch (e: HttpException){
//            return Result.failure()
//        }

        return  Result.success()
    }
}