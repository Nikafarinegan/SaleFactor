package ir.awlrhm.areminder.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.awlrhm.areminder.data.network.model.base.BaseResponse

internal class UserActivityResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result>?= null

    inner class Result{
        @SerializedName("tbL_UaID")
        val uaId: Long?= null

        @SerializedName("tbL_BaseTitle")
        val activityType: String?= null

        @SerializedName("tbL_ActivityTypeID_fk")
        val activityId: Long?= null

        @SerializedName("tbL_UaTitle")
        val title: String?= null

        @SerializedName("tbL_UaStartDate")
        val startDate: String?= null

        @SerializedName("tbL_UaStartTime")
        val startTime: String?= null

        @SerializedName("tbL_UaEndDate")
        val endDate: String?= null

        @SerializedName("tbL_UaEndTime")
        val endTime: String?= null

        @SerializedName("tbL_MlTitle")
        val meetingLocation: String?= null

        @SerializedName("tbL_MlID_fk")
        val meetingLocationId: Long?= null

        @SerializedName("tbL_UaAlarmDate")
        val alarmDate: String?= null

        @SerializedName("tbL_UaAlarmTime")
        val alarmTime: String?= null

        @SerializedName("tbL_UaNote")
        val note: String?= null

        @SerializedName("tbL_UaRegisterDate")
        var registerDate: String?= null

        @SerializedName("editMessage")
        val editMessage: String?= null

        @SerializedName("deleteMessage")
        val deleteMessage: String?= null

        //for model
        var dateTime: String?= null
    }
}