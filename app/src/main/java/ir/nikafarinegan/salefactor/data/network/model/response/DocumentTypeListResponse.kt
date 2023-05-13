package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class DocumentTypeListResponse : BaseResponse(){
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result {
        @SerializedName("tbL_FormID")
        val formId: Long?= null

        @SerializedName("tbL_FormFarsiName")
        val name: String?= null

        @SerializedName("tbL_FormParentID_fk")
        val formParentId: Long?= null

        @SerializedName("tbL_FormDescription")
        val description: String?= null

    }
}