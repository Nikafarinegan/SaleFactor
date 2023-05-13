package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class CustomerAccountByCustomerIdResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("tbL_CAID")
        val caId: Long?= null

        @SerializedName("tbL_CaSubject")
        val caSubject: String?= null

        @SerializedName("tbL_CaTitle")
        val caTitle: String?= null
    }
}