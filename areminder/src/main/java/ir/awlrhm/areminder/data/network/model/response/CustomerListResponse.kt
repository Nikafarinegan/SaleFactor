package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponse

internal class CustomerListResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result {
        @SerializedName("textMember")
        val textMember: String? = null

        @SerializedName("valueMember")
        val valueMember: Int? = null
    }
}