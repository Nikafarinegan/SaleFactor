package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

/**
 * Created by Ali_Kazemi on 25/10/2021.
 */
class DeleteDocumentSubDetailRequest: BaseRequest() {
    @SerializedName("whS_WdsdID")
    var wdsdId: Long?= null
}