package ng.com.techdepo.kotlincodelabs.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Single
import ng.com.techdepo.kotlincodelabs.database.MarsDatabase
import ng.com.techdepo.kotlincodelabs.mappers.toDomainModel
import ng.com.techdepo.kotlincodelabs.network.MarsApiService
import ng.com.techdepo.kotlincodelabs.network.MarsProperty
import javax.inject.Inject

class MarsRepository @Inject constructor( val marsDatabase: MarsDatabase,val marsApiService: MarsApiService) {

    val marsProperty: LiveData<List<MarsProperty>> = Transformations.map( marsDatabase.marsDAO.getMars()){
        it.toDomainModel()
    }

     fun refreshMars(filter: String):Single<List<MarsProperty>> {

    return marsApiService.getProperties(filter)
    }
}