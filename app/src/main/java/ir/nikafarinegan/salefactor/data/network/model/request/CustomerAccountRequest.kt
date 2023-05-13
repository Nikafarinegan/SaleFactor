package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 26/10/2021.
 */
class CustomerAccountRequest: BaseGetRequest() {
    @SerializedName("tbL_CaID")
    private val caId: Long = 0
}