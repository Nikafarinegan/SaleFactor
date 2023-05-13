package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class PlaceListResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result {
        @SerializedName("tbL_PlaceID")
        val placeId: Long ?= null
        @SerializedName("tbL_PlaceParentID_fk")
        val parentPlaceId: Long?= null

        @SerializedName("tbL_PlaceName")
        val placeName: String?= null

        @SerializedName("tbL_PlaceNote")
        val placeNote: String?= null
    }
}