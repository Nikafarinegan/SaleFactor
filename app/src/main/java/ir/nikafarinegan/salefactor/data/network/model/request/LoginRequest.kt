package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

data class LoginRequest(
    @SerializedName("userName")
    var userName: String? = null,

    @SerializedName("password")
var password: String? = null
): BaseRequest()