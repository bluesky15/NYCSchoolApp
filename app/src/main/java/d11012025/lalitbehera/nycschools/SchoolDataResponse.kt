package d11012025.lalitbehera.nycschools

import com.google.gson.annotations.SerializedName

data class SchoolDataResponse(
    @SerializedName("dbn") val id: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("city") val city: String,
    @SerializedName("zip") val zip: String
)