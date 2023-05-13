package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

class DeleteWarehouseDocumentDetailRequest: BaseRequest() {
    @SerializedName("whS_WddID")
    var wddId: Long?= null
}