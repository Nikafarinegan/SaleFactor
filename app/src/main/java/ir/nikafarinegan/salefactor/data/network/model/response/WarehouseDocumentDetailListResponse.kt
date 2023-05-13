package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class WarehouseDocumentDetailListResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result {
        @SerializedName("whS_WddID")
        val wddId: Long?= null

        @SerializedName("whS_GoodsID_fk")
        val goodId: String?= null

        @SerializedName("totalAmount")
        val amount: String?= null

        @SerializedName("goodsName")
        val goodsName: String?= null

        @SerializedName("whS_GuName")
        val unit: String?= null

        @SerializedName("whS_GcName")
        val gcName: String?= null

        @SerializedName("whS_WarehouseName")
        val warehouseName: String?= null

        @SerializedName("whS_WddNote")
        val note: String?= null

        @SerializedName("whS_WddSerialNo")
        val serialNo: String?= null

        @SerializedName("messageValidation")
        val messageValidation: String?= null
    }
}