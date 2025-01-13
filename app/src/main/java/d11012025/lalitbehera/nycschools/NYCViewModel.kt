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
class NYCViewModel @Inject constructor(val repository: NYCSRepository, val localCache: LocalCache) : ViewModel() {
    private val _schoolListData: MutableStateFlow<NetworkResult<List<SchoolDataResponse>>> =
        MutableStateFlow(
            NetworkResult.LOADING
        )
    val schoolListData: StateFlow<NetworkResult<List<SchoolDataResponse>>> =
        _schoolListData.asStateFlow()


    init {
        getSchoolData()
    }

    fun getSchoolData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSchoolData().collect {
                _schoolListData.value = it
            }
        }
    }

    fun updateId(id:String){
        localCache.id =id
    }
}