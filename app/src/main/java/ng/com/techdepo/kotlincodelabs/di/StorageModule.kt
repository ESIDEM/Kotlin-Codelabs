package ng.com.techdepo.kotlincodelabs.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ng.com.techdepo.kotlincodelabs.database.MarsDatabase
import ng.com.techdepo.kotlincodelabs.network.MarsApiService
import ng.com.techdepo.kotlincodelabs.repository.MarsRepository
import javax.inject.Singleton


@Module
open class StorageModule {



   private lateinit var INSTANCE: MarsDatabase





    @Provides
    @Singleton
    fun getDatabase(context: Context): MarsDatabase {
        synchronized(MarsDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MarsDatabase::class.java,
                        "mars")
                        .fallbackToDestructiveMigration()
                        .build()
            }
        }
        return INSTANCE
    }

    @Provides
    @Singleton
    fun getRepository(database: MarsDatabase,marsApiService: MarsApiService):MarsRepository{

        return MarsRepository(database,marsApiService)
    }

}