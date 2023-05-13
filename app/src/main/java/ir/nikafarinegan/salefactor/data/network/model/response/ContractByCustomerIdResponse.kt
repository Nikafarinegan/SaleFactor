package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class ContractByCustomerIdResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("cnT_ContractID")
        val contractId: Long?= null

        @SerializedName("cnT_ContractTitle")
        val contractTitle: String?= null

        @SerializedName("cnT_ContractNote")
        val contractNote: String?= null

        @SerializedName("cnT_ContractCode")
        val contractCode: String?= null

        @SerializedName("cnT_ContractStartDate")
        val contractStartDate: String?= null

        @SerializedName("cnT_ContractEndDate")
        val contractEndDate: String?= null

        @SerializedName("tbL_PlaceID_fk")
        val placeId: Long?= null
    }
}