package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BasePostRequest
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

class PostWarehouseDocumentRequest : BasePostRequest() {
    @SerializedName("whS_WdID")
    var wdId: Long? = null

    @SerializedName("tbL_FormID_fk")
    var formId: Long? = null

    @SerializedName("tbL_CustomerID_fk")
    var customerId: Long? = null

    @SerializedName("whS_WarehouseID_fk")
    var warehouseId: Long? = null

    @SerializedName("cnT_ContractID_fk")
    var contractId: Long? = null

    @SerializedName("woS_WoID_fk")
    var woId: Long? = null

    @SerializedName("tbL_PlaceID_fk")
    var placeId: Long? = null

    @SerializedName("buD_ProjectID_fk")
    var projectId: Long? = null

    @SerializedName("whS_WdCode")
    var wdCode: String? = null

    @SerializedName("whS_WdDate")
    var wdDate: String? = null

    @SerializedName("whS_WdReserveNo")
    var reserveNo: Long = 0

    @SerializedName("whS_ReserveWarehouseID_fk")
    var reserveWarehouseId: Long = 0

    @SerializedName("whS_WdParentID_fk")
    var wdParentId: Long = 0

    @SerializedName("whS_WdSubject")
    var wdSubject: String = ""

    @SerializedName("whS_WdReserveFinancialYearID")
    var reserveFinancialYearId: Int = 0

    @SerializedName("tbL_CaID_fk")
    var caId: Long = 0

    @SerializedName("whS_BolID_fk")
    private val bolId: Long = 0

    @SerializedName("acC_AdAdjustmentID_fk")
    private val adjustmentId: Long = 0

    @SerializedName("slP_SdID_fk")
    private val sdId: Long = 0

    @SerializedName("tbL_DepartmentID_fk")
    private val departmentId: Long = 0

    @SerializedName("whS_WdPriority")
    private val priority: Long = 0

    @SerializedName("whS_WdAgentInformation")
    private val agentInformation: String = ""

    @SerializedName("whS_WdBasculeDescription")
    var basculDescription: String = ""

    @SerializedName("whS_WdConfirmByUserDate")
    private val confirmByUserDate: String = ""

    @SerializedName("whS_WdConfirmByUserStatus")
    private val confirmByUserStatus: Int = 0

    @SerializedName("whS_WdRevokeStatus")
   private val revokeStatus: Int = 0

    @SerializedName("whS_WdRevokeDate")
    private val revokeDate: String = ""

    @SerializedName("whS_WdRevokeDescription")
    private val revokeDescription: String = ""

    @SerializedName("whS_WdLock")
    private val wdLock: Int = 0

    @SerializedName("whS_WdNote")
    var wdNote: String? = null

    @SerializedName("acC_AcID_fk")
    var acId: Long? = null

    @SerializedName("whS_WdRegisterDate")
    var registerDate: String?= null

    @SerializedName("whS_WdType")
    private val type = 0

    @SerializedName("whS_WdActive")
    private val active = 1

    @SerializedName("whS_WdStatus")
    private val status = 0

    @SerializedName("whS_WdDeleteDate")
    private val deleteDate: String = ""

}