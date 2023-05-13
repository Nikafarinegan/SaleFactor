package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

class DocumentNumberRequest: BaseGetRequest() {
    @SerializedName("whS_WdID")
    private val wdId: Long = 0
}