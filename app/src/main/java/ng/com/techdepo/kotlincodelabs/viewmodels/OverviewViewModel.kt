/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ng.com.techdepo.kotlincodelabs.viewmodels
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ng.com.techdepo.kotlincodelabs.network.MarsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ng.com.techdepo.kotlincodelabs.database.getDatabase
import ng.com.techdepo.kotlincodelabs.network.MarsApiFilter
import ng.com.techdepo.kotlincodelabs.network.MarsProperty
import ng.com.techdepo.kotlincodelabs.repository.MarsRepository
import java.io.IOException


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val marsRepository = MarsRepository(getDatabase(application))
    val marsList = marsRepository.marsProperty

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the response String
    val response: LiveData<MarsApiStatus>
        get() = _status

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(
            viewModelJob + Dispatchers.Main )

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        refreshDataFromRepository()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
//    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
//
//        coroutineScope.launch {
//            // Get the Deferred object for our Retrofit request
//            var getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
//            try {
//                _status.value = MarsApiStatus.LOADING
//
//                // Await the completion of our Retrofit request
//                var listResult = getPropertiesDeferred.await()
//
//                _status.value = MarsApiStatus.DONE
//
//                if (listResult.size > 0) {
//                    _property.value = listResult
//                }
//            } catch (e: Exception) {
//                _status.value = MarsApiStatus.ERROR
//                _property.value = ArrayList()
//            }
//        }
//    }

    private fun refreshDataFromRepository() {
        coroutineScope.launch {
            try {
                marsRepository.refreshMars()


            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(marsList.value!!.isEmpty())
                   Toast.makeText(getApplication(),"Couldn't fetch data",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    enum class MarsApiStatus { LOADING, ERROR, DONE }

//    fun updateFilter(filter: MarsApiFilter) {
//        getMarsRealEstateProperties(filter)
//    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}

