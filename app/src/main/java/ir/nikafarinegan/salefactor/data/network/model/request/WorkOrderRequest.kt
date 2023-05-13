package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 26/10/2021.
 */
class WorkOrderRequest: BaseGetRequest() {
    @SerializedName("woS_WoID")
    private val woId: Long = 0
}