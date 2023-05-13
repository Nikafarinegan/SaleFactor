package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class WarehouseListResponse : BaseResponse(){
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result{
        @SerializedName("valueMember")
        val valueMember: Long?= null

        @SerializedName("textMember")
        val textMember: String?= null
    }
}