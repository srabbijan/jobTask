package com.srabbijan.jobtask.data.remote.dto

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "demo_remote_data_table")
data class DemoRemoteData(
    @PrimaryKey
    @SerializedName("id"           ) var id           : Int,
    @SerializedName("title"        ) var title        : String?  = null,
    @SerializedName("thumbnailUrl" ) var thumbnailUrl : String?  = null,
    @SerializedName("duration"     ) var duration     : String?  = null,
    @SerializedName("uploadTime"   ) var uploadTime   : String?  = null,
    @SerializedName("views"        ) var views        : String?  = null,
    @SerializedName("author"       ) var author       : String?  = null,
    @SerializedName("videoUrl"     ) var videoUrl     : String?  = null,
    @SerializedName("description"  ) var description  : String?  = null,
    @SerializedName("subscriber"   ) var subscriber   : String?  = null,
    @SerializedName("isLive"       ) var isLive       : Boolean? = null
): Parcelable