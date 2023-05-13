package ir.awlrhm.areminder.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseRequest

internal class DeleteUserRequest: BaseRequest() {
    @SerializedName("tbL_UaID")
    var uaId: Long?= null
}