package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class CustomerResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result{
        @SerializedName("tbL_CustomerID")
        val customerId: Long?= null

        @SerializedName("tbL_CustomerTitle")
        val customerTitle: String?= null

        @SerializedName("tbL_CcNote")
        val ccNote: String?= null

        @SerializedName("tbL_CustomerParentID_fk")
        val customerParentId: Long?= null

        @SerializedName("tbL_CustomerUpdateDate")
        val customerUpdateDate: String?= null
    }
}