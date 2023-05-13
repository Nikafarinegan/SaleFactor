package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

class ChangePasswordRequest: BaseRequest() {
    @SerializedName("currentPassword")
    var currentPassword: String?= null

    @SerializedName("newPassword")
    var newPassword: String?= null

    @SerializedName("confirmPassword")
    var confirmPassword: String?= null
}