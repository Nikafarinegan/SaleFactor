package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class WorkOrderResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result> ?= null

    inner class Result{
        @SerializedName("woS_WoID")
        val woId: Long?= null

        @SerializedName("woS_WoParentID_fk")
        val woParentId: Long?= null

        @SerializedName("woS_WonID_fk")
        val wonId: Long?= null

        @SerializedName("woS_WonName")
        val wonName: String?= null

        @SerializedName("woS_WoTitle")
        val woTitle: String?= null

        @SerializedName("woS_WosName")
        val wosName: String?= null

        @SerializedName("woS_WoType")
        val woType: String?= null

        @SerializedName("tbL_PlaceID")
        val placeId: Long?= null

        @SerializedName("tbL_PlaceName")
        val placeName: String?= null

        @SerializedName("woS_WoPhysicalContent")
        val woPhysicalContent: Long?= null

        @SerializedName("woS_WoOldCode")
        val woOldCode: Long?= null

        @SerializedName("woS_WotName")
        val wotName: String?= null

        @SerializedName("woS_WoApproveDate")
        val woApproveDate: String?= null
    }
}