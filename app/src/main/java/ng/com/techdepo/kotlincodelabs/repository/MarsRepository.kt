package ng.com.techdepo.kotlincodelabs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ng.com.techdepo.kotlincodelabs.database.MarsDatabase
import ng.com.techdepo.kotlincodelabs.mappers.toDatabaseModel
import ng.com.techdepo.kotlincodelabs.mappers.toDomainModel
import ng.com.techdepo.kotlincodelabs.network.MarsApi
import ng.com.techdepo.kotlincodelabs.network.MarsProperty

class MarsRepository( private val marsDatabase: MarsDatabase) {

    val marsProperty: LiveData<List<MarsProperty>> = Transformations.map( marsDatabase.marsDAO.getMars()){
        it.toDomainModel()
    }

    suspend fun refreshMars(filter: String) {
        withContext(Dispatchers.IO) {
            var marsList = MarsApi.retrofitService.getProperties(filter).await()
            if (marsList.isEmpty()){
                return@withContext
            }else {
                marsDatabase.marsDAO.deleteAll()
                marsDatabase.marsDAO.insertAll(marsList.toDatabaseModel())
            }
        }
    }
}