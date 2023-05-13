package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class OperationResponse: BaseResponse() {
    @SerializedName("result")
    val result: Boolean?= null
}