package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

class CustomerRequest: BaseGetRequest() {
    @SerializedName("tbL_CustomerID")
    var customerId: Long?= null
}