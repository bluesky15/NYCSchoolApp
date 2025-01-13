package d11012025.lalitbehera.nycschools

import com.google.gson.annotations.SerializedName

data class SchoolSatScoreData(
    @SerializedName("dbn") val id: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("num_of_sat_test_takers") val numberOfSatTakers: String,
    @SerializedName("sat_critical_reading_avg_score") val satCriticalReadingAverageScore: String,
    @SerializedName("sat_math_avg_score") val satMathAvgScore: String,
    @SerializedName("sat_writing_avg_score") val satWritingAvgScore: String,
)