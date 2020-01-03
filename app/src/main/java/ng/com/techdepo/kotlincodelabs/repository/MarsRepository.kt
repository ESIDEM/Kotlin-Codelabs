package ng.com.techdepo.kotlincodelabs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ng.com.techdepo.kotlincodelabs.database.MarsDatabase
import ng.com.techdepo.kotlincodelabs.mappers.toDatabaseModel
import ng.com.techdepo.kotlincodelabs.mappers.toDomainModel
import ng.com.techdepo.kotlincodelabs.network.MarsApiService
import ng.com.techdepo.kotlincodelabs.network.MarsProperty
import javax.inject.Inject

class MarsRepository @Inject constructor( val marsDatabase: MarsDatabase,val marsApiService: MarsApiService) {

    val marsProperty: LiveData<List<MarsProperty>> = Transformations.map( marsDatabase.marsDAO.getMars()){
        it.toDomainModel()
    }

    suspend fun refreshMars(filter: String) {
        withContext(Dispatchers.IO) {
            var marsList = marsApiService.getProperties(filter).await()
            if (marsList.isEmpty()){
                return@withContext
            }else {
                marsDatabase.marsDAO.deleteAll()
                marsDatabase.marsDAO.insertAll(marsList.toDatabaseModel())
            }
        }
    }
}