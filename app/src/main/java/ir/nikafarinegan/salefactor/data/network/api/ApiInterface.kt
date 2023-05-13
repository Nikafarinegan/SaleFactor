package ir.nikafarinegan.salefactor.data.network.api

import ir.nikafarinegan.salefactor.data.network.model.request.*
import ir.nikafarinegan.salefactor.data.network.model.response.*
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    /** #Begin Base apis ========================================================================**/
    @POST("Authenticate/Authentication")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("NIK_UnhandledException/NIK_Ue_Insert_MobAPI")
    fun postUnhandledException(
        @Body request: PostUnhandledExceptionRequest
    ): Call<ResponseId>

    @POST("TBL_User/TBL_User_SpecialOperation_MobAPI")
    fun postChangePassword(
        @Body request: BaseGetRequest
    ): Call<ResponseId>

    @POST("TBL_UserSmartDevice/TBL_Usd_GetData_MobAPI")
    fun getUserSmartDevice(
        @Body request: UserSmartDeviceRequest
    ): Call<UserSmartDeviceResponse>

    @POST("TBL_UserSmartDevice/TBL_Usd_GetData_MobAPI")
    fun isExistUserSmartDevice(
        @Body request: UserSmartDeviceRequest
    ): Call<UserSmartDeviceResponse>

    @POST("TBL_UserSmartDevice/TBL_Usd_SpecialOperation_MobAPI")
    fun postUserSmartDevice(
        @Body request: UserSmartDeviceRequest
    ): Call<ResponseId>

    @POST("TBL_UserSmartDevice/TBL_Usd_SpecialOperation_MobAPI")
    fun getCheckConfirmCode(
        @Body request: UserSmartDeviceRequest
    ): Call<ResponseId>

    @POST("TBL_User/TBL_User_SpecialOperation_MobAPI")
    fun postPasswordRecover(
        @Body request: BaseGetRequest
    ): Call<ResponseId>

    @POST("TBL_User/TBL_User_GetData_MobAPI")
    fun getPersonnelInformation(
        @Body request: BaseGetRequest
    ): Call<PersonnelResponse>

    @POST("NIK_SubSystem/NIK_Ss_GetData_MobAPI")
    fun getSubSystemList(
        @Body request: BaseGetRequest
    ): Call<SubSystemResponse>


    /** #End Base apis ======================================================================== **/
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- End Base Apis ---------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    //
    //
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- Begin Operation -------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin Warehouse Document ============================================================== **/
    @POST("ACC_FinancialYear/ACC_Fy_GetData_MobAPI")
    fun getFinancialYearList(
        @Body request: FinancialYearRequest
    ): Call<FinancialYearResponse>

    @POST("WHS_Warehouse/WHS_Warehouse_GetData_MobAPI")
    fun getWarehouseList(
        @Body request: WarehouseListRequest
    ): Call<WarehouseListResponse>

    @POST("TBL_Form/TBL_Form_GetData_MobAPI")
    fun getWarehouseDocumentTypeList(
       @Body request: DocumentTypeListRequest
    ): Call<DocumentTypeListResponse>

    @POST("WHS_WarehouseDocument/WHS_Wd_GetData_MobAPI")
    fun getWarehouseDocumentList(
        @Body request: WarehouseDocumentListRequest
    ): Call<WarehouseDocumentListResponse>

    @HTTP(method = "DELETE", path = "WHS_WarehouseDocument/WHS_Wd_Delete_MobAPI", hasBody = true)
    fun deleteWarehouseDocument(
        @Body request: DeleteWarehouseDocumentRequest
    ):Call<ResponseId>

    /** #Begin Warehouse Document ============================================================== **/
    //
    //
    //
    //
    //
    /** #Begin Warehouse Document Detail ========================================================**/
    @POST("WHS_WarehouseDocumentDetail/WHS_Wdd_GetData_MobAPI")
    fun getWarehouseDocumentDetailList(
        @Body request: WarehouseDocumentDetailRequest
    ): Call<WarehouseDocumentDetailListResponse>

    @POST("WHS_Goods/WHS_Goods_GetData_MobAPI")
    fun getSearchGoodsList(
        @Body request: SearchGoodsListRequest
    ): Call<WHSGoodsListResponse>

    @POST("WHS_WarehouseDocumentDetail/WHS_Wdd_InsertSpecific_MobAPI")
    fun insertDocumentDetail(
        @Body request: PostDocumentDetailRequest
    ): Call<ResponseId>

    @POST("WHS_WarehouseDocumentDetail/WHS_Wdd_UpdateSpecific_MobAPI")
    fun updateDocumentDetail(
        @Body request: PostDocumentDetailRequest
    ): Call<ResponseId>

    @HTTP(method = "DELETE", path = "WHS_WarehouseDocumentDetail/WHS_Wdd_Delete_MobAPI", hasBody = true)
    fun deleteWarehouseDocumentDetail(
        @Body request: DeleteWarehouseDocumentDetailRequest
    ):Call<ResponseId>

    /** #End Warehouse Document Detail ==========================================================**/
    //
    //
    //
    //
    //
    /** #Begin Warehouse Document Sub Detail =================================================== **/
    @POST("WHS_WarehouseDocumentSDetail/WHS_Wdsd_GetData_MobAPI")
    fun getWarehouseDocumentSubDetailList(
        @Body request: WarehouseDocumentSubDetailRequest
    ): Call<WarehouseDocumentSubDetailResponse>

    @POST("WHS_WarehouseDocumentSDetail/WHS_Wdsd_Insert_MobAPI")
    fun insertDocumentSubDetail(
        @Body request: PostDocumentSubDetailRequest
    ): Call<ResponseId>

    @HTTP(method = "DELETE", path = "WHS_WarehouseDocumentSDetail/WHS_Wdsd_Delete_MobAPI", hasBody = true)
    fun deleteDocumentSubDetail(
        @Body request: DeleteDocumentSubDetailRequest
    ): Call<ResponseId>

    /** #End Warehouse Document Sub Detail ===================================================== **/
    //
    //
    //
    //
    //
    /** #Begin Add Document ==================================================================== **/
    @POST("TBL_Place/TBL_Place_GetData_MobAPI")
    fun getPlaceList(
        @Body request: PlaceRequest
    ): Call<PlaceListResponse>

    @POST("WOS_WorkOrder/WOS_Wo_GetData_MobAPI")
    fun getWorkOrderList(
        @Body request: WorkOrderRequest
    ): Call<WorkOrderResponse>

    @POST("BUD_Project/BUD_Project_GetData_MobAPI")
    fun getProjectList(
        @Body request: ProjectListRequest
    ):Call<ProjectListResponse>

    @POST("TBL_Customer/TBL_Customer_GetData_MobAPI")
    fun getCustomerSearchList(
        @Body request: CustomerSearchListRequest
    ): Call<CustomerSearchListResponse>

    @POST("CNT_Contract/CNT_Contract_GetData_MobAPI")
    fun getContract(
        @Body request : ContractRequest
    ):Call<ContractByCustomerIdResponse>

    @POST("WHS_WarehouseDocument/WHS_Wd_GetData_MobAPI")
    fun getDocumentNumber(
        @Body request: DocumentNumberRequest
    ): Call<WarehouseDocumentListResponse>

    @POST("WHS_WarehouseDocument/WHS_Wd_Insert_MobAPI")
    fun insertWarehouseDocument(
        @Body request: PostWarehouseDocumentRequest
    ):Call<ResponseId>

    @POST("WHS_WarehouseDocument/WHS_Wd_Update_MobAPI")
    fun updateWarehouseDocument(
        @Body request: PostWarehouseDocumentRequest
    ):Call<ResponseId>

    @POST("WHS_WarehouseDocument/WHS_Wd_GetData_MobAPI")
    fun getReserveWarehouseDocument(
        @Body request: WarehouseDocumentListRequest
    ): Call<WarehouseDocumentListResponse>

    @POST("TBL_CustomerAccount/TBL_Ca_GetData_MobAPI")
    fun getCustomerAccountByCustomerId(
        @Body request: CustomerAccountRequest
    ): Call<CustomerAccountByCustomerIdResponse>

    @POST("ACC_AccountingCode/ACC_Ac_GetData_MobAPI")
    fun getAccountingCode(
        @Body request: AccountingCodeReqeust
    ): Call<AccountingCodeResponse>


    /** #End Add Document ====================================================================== **/
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- End Operation ---------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    //
    //
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- Begin Base Information ------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin Base Information ================================================================ **/
    @POST("TBL_Customer/TBL_Customer_GetData_MobAPI")
    fun getCustomer(
        @Body request: CustomerRequest
    ): Call<CustomerResponse>





    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- End Base Information --------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #End Base Information ================================================================== **/
}