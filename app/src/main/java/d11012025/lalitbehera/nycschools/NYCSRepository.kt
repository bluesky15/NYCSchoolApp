package d11012025.lalitbehera.nycschools

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NYCSRepository @Inject constructor(val apiService: ApiService) {
    fun getSchoolData(): Flow<NetworkResult<List<SchoolDataResponse>>> {
        return flow {
            emit(NetworkResult.LOADING)
            try {
                val result = apiService.getSchoolData()
                emit(NetworkResult.SUCCESS(result))
            } catch (e: Exception) {
                emit(NetworkResult.ERROR(e))
            }
        }
    }

    fun getSatScore(): Flow<NetworkResult<List<SchoolSatScoreData>>> {
        return flow {
            emit(NetworkResult.LOADING)
            try {
                val result = apiService.getSchoolSatScoreData()
                emit(NetworkResult.SUCCESS(result))
            } catch (e: Exception) {
                emit(NetworkResult.ERROR(e))
            }
        }
    }

}