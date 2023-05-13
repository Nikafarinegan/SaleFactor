package ir.nikafarinegan.salefactor.data.network.model.base

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest
import ir.nikafarinegan.salefactor.utils.Const

open class BaseGetRequest: BaseRequest() {
    @SerializedName("jsonParameters")
    var jsonParameters: String?= null

    @SerializedName("pageNumber")
    var pageNumber: Int?= null

    @SerializedName("pageSize")
    var pageSize : Int= Const.PAGE_SIZE

    @SerializedName("typeOperation")
    var typeOperation: Int = 0
}