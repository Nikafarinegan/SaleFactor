package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse


class UserSmartDeviceResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("tbL_UsdID")
        val usdId: Long?= null

        @SerializedName("tbL_UserId_fk")
        val userId: Long?= null

        @SerializedName("niK_SsID_fk")
        val ssId: Int?= null

        @SerializedName("tbL_UsdImeiNumber")
        val imei: String?= null

        @SerializedName("tbL_UsdMobileNo")
        val mobileNo: String?= null

        @SerializedName("tbL_UsdOsType")
        val osType: String?= null

        @SerializedName("tbL_UsdOsVersion")
        val osVersion: String?= null

        @SerializedName("tbL_UsdModel")
        val model: String?= null

        @SerializedName("tbL_UsdRegisterDate")
        val registerDate: String?= null

        @SerializedName("tbL_UsdType")
        val type: Int?= null

        @SerializedName("tbL_UsdActive")
        val active: Int?= null

        @SerializedName("tbL_UsdStatus")
        val status: Int?= null
    }
}