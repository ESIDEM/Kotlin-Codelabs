package ng.com.techdepo.kotlincodelabs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MarsPropertyEntity::class],version = 1)
abstract class MarsDatabase: RoomDatabase() {

    abstract val marsDAO: MarsDAO
}

private lateinit var INSTANCE: MarsDatabase

fun getDatabase(context: Context): MarsDatabase {
    synchronized(MarsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MarsDatabase::class.java,
                    "mars").build()
        }
    }
    return INSTANCE
}