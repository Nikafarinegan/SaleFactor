package ir.nikafarinegan.salefactor.data.network.model.response

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse

class WarehouseDocumentListResponse: BaseResponse() {
    @SerializedName("result")
    val result: MutableList<Result> ?= null

    inner class Result {
        @SerializedName("whS_WdID")
        val wdId: Long?= null

        @SerializedName("whS_WdSubject")
        val wdSubject: String?= null

        @SerializedName("whS_WdReserveNo")
        val reserveNo: Long?= null

        @SerializedName("whS_WdReserveName")
        val reserveName: String?= null

        @SerializedName("whS_ReserveWarehouseID_fk")
        val reserveWarehouseId: Long?= null

        @SerializedName("whS_ReserveWarehouseName")
        val reserveWarehouseName: String?= null

        @SerializedName("acID")
        val acId: Long?= null

        @SerializedName("acC_AcNameTemp")
        val acNameTemp: String?= null

        @SerializedName("whS_WdReserveFinancialYearID")
        val reserveFinancialYearId: Int?= null

        @SerializedName("tbL_PlaceID_fk")
        val placeId: Long?= null

        @SerializedName("tbL_PlaceNote")
        val placeNote: String?= null

        @SerializedName("tbL_UserID")
        val userId: Long?= null

        @SerializedName("registrarUser")
        val registerUser: String?= null

        @SerializedName("woS_WoID_fk")
        val woId: Long?= null

        @SerializedName("woS_WoTitle")
        val woTitle: String?= null

        @SerializedName("buD_ProjectID_fk")
        val projectId: Long?= null

        @SerializedName("buD_ProjectName")
        val projectName: String?= null

        @SerializedName("tbL_CustomerID_fk")
        val customerId: Long?= null

        @SerializedName("tbL_CustomerTitle")
        val customerTitle: String?= null

        @SerializedName("cnT_ContractID_fk")
        val contractId: Long?= null

        @SerializedName("cnT_ContractTitle")
        val contractTitle: String?= null

        @SerializedName("whS_WdParentID_fk")
        val wdParentId: Long?= null

        @SerializedName("tbL_FormID_fk")
        val formId: Long?= null

        @SerializedName("tbL_FormFarsiName")
        val formName: String?= null

        @SerializedName("whS_WarehouseID_fk")
        val warehouseId: Long?= null

        @SerializedName("whS_WarehouseName")
        val warehouseName: String?= null

        @SerializedName("whS_WdDate")
        val wdDate: String?= null

        @SerializedName("whS_WdCode")
        val wdCode: String?= null

        @SerializedName("whS_WdNote")
        val note: String?= null

        @SerializedName("whS_WdRegisterDate")
        val registerDate: String?= null

        @SerializedName("acC_FinancialYearID")
        val financialYearId: Long?= null

        @SerializedName("tbL_CaID_fk")
        val caId: Long?= null

        @SerializedName("tbL_CaCode")
        val caCode: Long?= null

        @SerializedName("tbL_CaSubject")
        val caSubject: String?= null

        @SerializedName("whS_WdBasculeDescription")
        val weighBridgeDescription: String?= null

        @SerializedName("messageValidation")
        val messageValidation: String?= null

        @SerializedName("maxCode")
        val maxCode: Long?= null
    }
}