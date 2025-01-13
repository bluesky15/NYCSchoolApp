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

typealias SchoolDataList = NetworkResult<List<SchoolDataResponse>>

@HiltViewModel
class NYCViewModel @Inject constructor(val repository: NYCSRepository) : ViewModel() {
    var cachedData: NetworkResult<List<SchoolSatScoreData>>? = null
    var selectedID = ""

    private val _schoolSatListData: MutableStateFlow<SchoolSatScoreData?> = MutableStateFlow(null)
    val schoolSatListData: StateFlow<SchoolSatScoreData?> = _schoolSatListData.asStateFlow()

    private val _schoolListData: MutableStateFlow<SchoolDataList> = MutableStateFlow(NetworkResult.LOADING)
    val schoolListData: StateFlow<SchoolDataList> = _schoolListData.asStateFlow()


    init {
        getSchoolData()
        getSatData()
    }

    fun getSchoolData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSchoolData().collect {
                _schoolListData.value = it
            }
        }
    }

    fun getSatData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSatScore().collect {
                if (it is NetworkResult.SUCCESS) {
                    cachedData = it
                    filterData()
                }
            }
        }
    }

    fun filterData() {
        viewModelScope.launch {
            val school =
                (cachedData as NetworkResult.SUCCESS<List<SchoolSatScoreData>>).data.filter { it.id == selectedID }
            school.firstOrNull()?.let {
                _schoolSatListData.value = it
            }
        }
    }
}