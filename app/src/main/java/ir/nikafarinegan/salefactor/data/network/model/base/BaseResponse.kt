package ir.nikafarinegan.salefactor.data.network.model.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse: Serializable {
    @SerializedName("status")
    var status: Boolean?= null

    @SerializedName("message")
    var message: String?= null

    @SerializedName("statusDescription")
    var statusDescription: Int?= null

    @SerializedName("resultCount")
    val resultCount: Long?= null

    @SerializedName("dateTime")
    val dateTime: String?= null
}