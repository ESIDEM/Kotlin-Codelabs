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
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ng.com.techdepo.kotlincodelabs.MarsApiFilter
import ng.com.techdepo.kotlincodelabs.MarsApp
import ng.com.techdepo.kotlincodelabs.network.MarsProperty
import ng.com.techdepo.kotlincodelabs.repository.MarsRepository
import java.io.IOException
import javax.inject.Inject


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel @Inject constructor(val marsRepository: MarsRepository)
    : ViewModel() {


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
        refreshDataFromRepository(MarsApiFilter.SHOW_ALL)
    }


    private fun refreshDataFromRepository(filter: MarsApiFilter) {
        coroutineScope.launch {
            try {
                marsRepository.refreshMars(filter.value)
                _status.value = MarsApiStatus.DONE


            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(marsList.value!!.isEmpty())
                    _status.value = MarsApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    enum class MarsApiStatus { LOADING, ERROR, DONE }

    fun updateFilter(filter: MarsApiFilter) {
        refreshDataFromRepository(filter)
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}

