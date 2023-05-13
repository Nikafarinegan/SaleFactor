package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class ProjectListResponse: BaseResponse() {
    @SerializedName("result")
    val result: List<Result>?= null

    inner class Result{
        @SerializedName("buD_ProjectID")
        val projectId: Long?= null

        @SerializedName("buD_ProjectParentID_fk")
        val projectParentId: Long?= null

        @SerializedName("buD_ProjectName")
        val projectName: String?= null

        @SerializedName("pmS_PcName")
        val pcName: String?= null

        @SerializedName("pmS_PsName")
        val psName: String?= null

        @SerializedName("buD_ProjectNote")
        val projectNote: String?= null

        @SerializedName("buD_ProjectCode")
        val projectCode: String?= null

        @SerializedName("buD_ProjectBudgetTitle")
        val projectBudgetTitle: String?= null

        @SerializedName("buD_ProjectActualPercentageProgress")
        val projectActualPercentageProgress: Int?= null

        @SerializedName("buD_ProjectPlanPercentageProgress")
        val projectPlanPercentageProgress: Int?= null

        @SerializedName("buD_ProjectEstimateStartDate")
        val projectEstimateStartDate: String?= null

        @SerializedName("buD_ProjectEstimateEndDate")
        val projectEstimateEndDate: String?= null

        @SerializedName("buD_ProjectPrice")
        val projectPrice: Long?= null

        @SerializedName("buD_ProjectLastUpdateDate")
        val projectLastUpdate: String?= null
    }
}