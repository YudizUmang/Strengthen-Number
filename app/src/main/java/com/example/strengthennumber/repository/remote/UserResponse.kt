package com.example.strengthennumber.repository.remote

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data" ) var data : Data? = Data(),
    @SerializedName("meta" ) var meta : Meta? = Meta()
)

data class Data (

    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("username"       ) var userName      : String? = null,
    @SerializedName("name"           ) var name          : String? = null,
    @SerializedName("email"          ) var email         : String? = null,
    @SerializedName("contact_number" ) var contactNumber : String? = null,
    @SerializedName("dob"            ) var dob           : String? = null,
    @SerializedName("bio"            ) var bio           : String? = null,
    @SerializedName("gender"         ) var gender        : String? = null,
    @SerializedName("latitude"       ) var latitude      : String? = null,
    @SerializedName("longitude"      ) var longitude     : String? = null,
    @SerializedName("fitness_level"  ) var fitnessLevel  : String? = null,
    @SerializedName("interests"      ) var interests     : Set<String>? = null,
    @SerializedName("profile_photo"  ) var profilePhoto  : String? = null,
    @SerializedName("registered_at"  ) var registerAt    : String? = null,
    @SerializedName("total_followers") var followers     : Int? = null,
    @SerializedName("total_followings") var following    : Int? = null,
    @SerializedName("is_blocked"     ) var isBlocked     : Int? = null

)

data class Meta (

    @SerializedName("message" ) var message : String? = null

)