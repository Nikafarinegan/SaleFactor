package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

class DeleteWarehouseDocumentRequest : BaseRequest(){
    @SerializedName("whS_WdID")
    var wdId: Long?= null
}