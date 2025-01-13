package d11012025.lalitbehera.nycschools

import retrofit2.http.GET

interface ApiService {
    @GET("/resource/s3k6-pzi2.json")
    suspend fun getSchoolData():List<SchoolDataResponse>

    @GET("/resource/f9bf-2cp4.json")
    suspend fun getSchoolSatScoreData(): List<SchoolSatScoreData>
}