package d11012025.lalitbehera.nycschools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SATViewModel @Inject constructor(val repository: NYCSRepository, val localCache: LocalCache): ViewModel(){
    var cachedData: NetworkResult<List<SchoolSatScoreData>>? = null

    private val _schoolSatListData: MutableStateFlow<SchoolSatScoreData?> = MutableStateFlow(null)
    val schoolSatListData: StateFlow<SchoolSatScoreData?> =
        _schoolSatListData.asStateFlow()

    fun getSatData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSatScore().collect {
                if(it is NetworkResult.SUCCESS){
                    cachedData = it
                    filterData()
                }
            }
        }
    }

    fun filterData() {
        viewModelScope.launch{
            val school  =(cachedData as NetworkResult.SUCCESS<List<SchoolSatScoreData>>).data.filter { it.id == localCache.id }
            school.firstOrNull()?.let {
                _schoolSatListData.value = it
            }
        }
    }
}