package com.example.strengthennumber.repository.remote

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("data" ) var data : Data? = Data(),
    @SerializedName("meta" ) var meta : Meta? = Meta()
)

data class Data (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("email"          ) var email         : String? = null,
    @SerializedName("contact_number" ) var contactNumber : String? = null,
    @SerializedName("dob"            ) var dob           : String? = null,
    @SerializedName("bio"            ) var bio           : String? = null,
    @SerializedName("gender"         ) var gender        : String? = null,
    @SerializedName("latitude"       ) var latitude      : String? = null,
    @SerializedName("longitude"      ) var longitude     : String? = null,
    @SerializedName("fitness_level"  ) var fitnessLevel  : String? = null,
    @SerializedName("interests"      ) var interests     : String? = null,
    @SerializedName("profile_photo"  ) var profilePhoto  : String? = null

)

data class Meta (

    @SerializedName("message" ) var message : String? = null

)