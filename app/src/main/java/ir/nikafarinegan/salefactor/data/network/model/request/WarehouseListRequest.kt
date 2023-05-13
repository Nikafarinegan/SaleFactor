package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 23/10/2021.
 */
class WarehouseListRequest: BaseGetRequest() {
    @SerializedName("whS_WarehouseID")
    private val warehouseId: Long = 0
}