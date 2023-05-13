package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

class PasswordRecoverRequest: BaseRequest() {
    @SerializedName("userName")
    var userName: String?= null
}