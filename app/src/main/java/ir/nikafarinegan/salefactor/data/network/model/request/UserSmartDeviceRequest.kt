package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

/**
 * Created by Ali_Kazemi on 21/09/2021.
 */
class UserSmartDeviceRequest: BaseRequest() {

    @SerializedName("tbL_UsdID")
    private val usdId: Long= 0

    @SerializedName("typeOperation")
    var typeOperation: Int = 0

    @SerializedName("jsonParameters")
    var jsonParameters: String?= null
}