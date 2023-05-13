package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class WHSGoodsListResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result {
        @SerializedName("whS_WarehouseID_fk")
        val warehouseId: Long?= null

        @SerializedName("whS_WarehouseName")
        val warehouseName: String?= null

        @SerializedName("whS_GoodsID")
        val goodsId: String?= null

        @SerializedName("whS_GoodsFarsiName")
        val goodsName: String?= null

        @SerializedName("whS_GoodsEnglishName")
        val goodsEnglishName: String?= null

        @SerializedName("whS_GoodsModel")
        val goodsModel: String?= null

        @SerializedName("whS_GoodsNote")
        val goodsNote: String?= null

        @SerializedName("whS_OldGoodsID")
        val oldGoodsId: Long?= null

        @SerializedName("whS_OldGoodsFarsiName")
        val oldGoodsName: String?= null

        @SerializedName("remain")
        val remain: Int?= null

        @SerializedName("whS_GuName")
        val unit: String?= null
    }
}