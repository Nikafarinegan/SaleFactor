package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class AccountingCodeResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("acC_AcID")
        val acId: Long?= null

        @SerializedName("acC_AcName")
        val acName: String?= null

        @SerializedName("tbL_ResourceName")
        val resourceName: String?= null

        @SerializedName("acC_AcqName")
        val acqName: String?= null

        @SerializedName("acC_AcNote")
        val acNote: String?= null
    }
}