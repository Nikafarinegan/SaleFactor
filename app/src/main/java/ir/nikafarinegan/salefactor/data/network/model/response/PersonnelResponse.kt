package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class PersonnelResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>? = null

    inner class Result {
        @SerializedName("tbL_CustomerID_fk")
        val personnelId: Long? = null

        @SerializedName("tbL_UserID")
        val userId: Long? = null

        @SerializedName("tbL_UserMobileNo")
        val mobile: String? = null

        @SerializedName("tbL_CustomerTitle")
        val personnelNameTitle: String? = null

        @SerializedName("tbL_PostID_fk")
        val postId: String? = null

        @SerializedName("tbL_PostTitle")
        val postTitle: String? = null

        @SerializedName("tbL_UserPicture")
        val thumbnail: String? = null
    }
}