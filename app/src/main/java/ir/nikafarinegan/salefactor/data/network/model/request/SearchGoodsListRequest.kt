package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 25/10/2021.
 */
class SearchGoodsListRequest: BaseGetRequest() {
    @SerializedName("whS_GoodsID")
    private val goodsId: Long = 0
}