package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 25/10/2021.
 */
class WarehouseDocumentDetailRequest: BaseGetRequest() {
    @SerializedName("whS_WddID")
    private val wddId: Long = 0
}