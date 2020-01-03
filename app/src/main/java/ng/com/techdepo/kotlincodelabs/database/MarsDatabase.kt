package ng.com.techdepo.kotlincodelabs.database


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarsPropertyEntity::class],version = 1)
abstract class MarsDatabase: RoomDatabase() {

    abstract val marsDAO: MarsDAO
}

