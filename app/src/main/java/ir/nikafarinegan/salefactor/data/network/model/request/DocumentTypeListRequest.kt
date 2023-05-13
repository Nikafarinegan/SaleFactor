package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 25/10/2021.
 */
class DocumentTypeListRequest: BaseGetRequest() {
    @SerializedName("tbL_FormID")
    private val formId: Long = 0
}