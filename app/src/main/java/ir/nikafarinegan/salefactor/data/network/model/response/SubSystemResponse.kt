package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class SubSystemResponse: BaseResponse() {

    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result {
        @SerializedName("niK_SsID")
        val ssId: Int?= null

        @SerializedName("niK_SsFarsiName")
        val name: String?= null

        @SerializedName("tbL_RiBody")
        val body: String?= null

        @SerializedName("niK_SsParentID_fk")
        val parentId: Long?= null
    }
}