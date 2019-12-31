package ng.com.techdepo.kotlincodelabs.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MarsDAO {


    @Query("select * from marspropertyentity")
    fun getMars(): LiveData<List<MarsPropertyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( mars: List<MarsPropertyEntity>)

    @Query("delete from marspropertyentity")
    fun deleteAll()
}