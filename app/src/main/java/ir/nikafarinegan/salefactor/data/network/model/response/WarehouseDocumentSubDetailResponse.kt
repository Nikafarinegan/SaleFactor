package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class WarehouseDocumentSubDetailResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result{
        @SerializedName("whS_WdsdID")
        val wdsdId: Long?= null

        @SerializedName("whS_GoodsID")
        val goodsId: String?= null

        @SerializedName("whS_GoodsFarsiName")
        val goodsName: String?= null

        @SerializedName("whS_WdsdSerialNo")
        val serialNum: String?= null

        @SerializedName("whS_WdsdReusable")
        val reusable: Int?= null

        @SerializedName("whS_WdsdInputAmount")
        val inputAmount: Int?= null

        @SerializedName("whS_WdsdOutputAmount")
        val outputAmount: Int?= null

        @SerializedName("whS_WdsdReturnAmount")
        val returnAmount: Int?= null

        @SerializedName("whS_WdsdTotalInputPrice")
        val totalInputPrice: Long?= null

        @SerializedName("whS_WdsdTotalOutputPrice")
        val totalOutputPrice: Long?= null

        @SerializedName("whS_WdsdTotalReturnPrice")
        val totalReturnPrice: Long?= null

        @SerializedName("whS_WdsdNote")
        val note: String?= null

        @SerializedName("whS_WdsdRegisterDate")
        val registeredDate: String?= null

        @SerializedName("messageValidation")
        val messageValidation: String?= null
    }
}