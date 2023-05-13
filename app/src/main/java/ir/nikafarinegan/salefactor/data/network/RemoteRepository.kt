package ir.nikafarinegan.salefactor.data.network

import android.content.Context
import android.content.Intent
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.api.ApiCallback
import ir.nikafarinegan.salefactor.data.network.api.ApiInterface
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.*
import ir.nikafarinegan.salefactor.data.network.model.response.*
import ir.nikafarinegan.salefactor.utils.ErrorKey
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.view.login.LoginActivity
import okhttp3.Headers

class RemoteRepository(
    private val context: Context,
    private val pref: PreferenceConfiguration,
    private val api: ApiInterface
) {

    interface OnApiCallback<Model> {
        fun onDataLoaded(data: Model)
        fun onError(response: BaseResponse?)
    }

    private fun handleError(body: BaseResponse) {
        when (body.statusDescription) {
            ErrorKey.AUTHORIZATION -> context.showLogin()
        }
    }

    private fun Context.showLogin() {
        yToast(
            getString(R.string.error_finish_time),
            MessageStatus.ERROR
        )
        pref.isLogout = true
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////

    //-------------------------------------- #Begin Base Apis -------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin Basic apis ====================================================================== **/

    fun login(request: LoginRequest, callback: OnApiCallback<LoginResponse>) {
        val call = api.login(request)

        call.enqueue(object : ApiCallback<LoginResponse>(context) {
            override fun response(response: LoginResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun postPasswordRecover(
        request: BaseGetRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postPasswordRecover(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postChangePassword(
        request: BaseGetRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postChangePassword(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getPersonnelInformation(
        request: BaseGetRequest,
        callback: OnApiCallback<PersonnelResponse>
    ) {
        val call = api.getPersonnelInformation(request)
        call.enqueue(object : ApiCallback<PersonnelResponse>(context) {
            override fun response(response: PersonnelResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun postUserSmartDevice(
        request: UserSmartDeviceRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postUserSmartDevice(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun isExistUserSmartDevice(
        request: UserSmartDeviceRequest,
        callback: OnApiCallback<UserSmartDeviceResponse>
    ) {
        val call = api.isExistUserSmartDevice(request)
        call.enqueue(object : ApiCallback<UserSmartDeviceResponse>(context) {
            override fun response(response: UserSmartDeviceResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getCheckConfirmCode(
        request: UserSmartDeviceRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.getCheckConfirmCode(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun postUnhandledException(
        request: PostUnhandledExceptionRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.postUnhandledException(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getUserSmartDevice(
        request: UserSmartDeviceRequest,
        callback: OnApiCallback<UserSmartDeviceResponse>
    ) {
        val call = api.getUserSmartDevice(request)
        call.enqueue(object : ApiCallback<UserSmartDeviceResponse>(context) {
            override fun response(response: UserSmartDeviceResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getSubSystemList(
        request: BaseGetRequest,
        callback: OnApiCallback<SubSystemResponse>
    ) {
        val call = api.getSubSystemList(request)
        call.enqueue(object : ApiCallback<SubSystemResponse>(context) {
            override fun response(response: SubSystemResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }
    /** #End Basic apis ======================================================================== **/
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //-------------------------------------- #End Base Apis ---------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    //
    //
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- Begin Warehouse -------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin Warehouse ======================================================================= **/

    fun getFinancialYearList(
        request: FinancialYearRequest,
        callback: OnApiCallback<FinancialYearResponse>
    ) {
        val call = api.getFinancialYearList(request)
        call.enqueue(object : ApiCallback<FinancialYearResponse>(context) {
            override fun response(response: FinancialYearResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getWarehouseList(
        request: WarehouseListRequest,
        callback: OnApiCallback<WarehouseListResponse>) {
        val call = api.getWarehouseList(request)
        call.enqueue(object : ApiCallback<WarehouseListResponse>(context) {
            override fun response(response: WarehouseListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getWarehouseDocumentTypeList(
        request: DocumentTypeListRequest,
        callback: OnApiCallback<DocumentTypeListResponse>
    ) {
        val call = api.getWarehouseDocumentTypeList(request)
        call.enqueue(object : ApiCallback<DocumentTypeListResponse>(context) {
            override fun response(response: DocumentTypeListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getWarehouseDocumentList(
        request: WarehouseDocumentListRequest,
        callback: OnApiCallback<WarehouseDocumentListResponse>
    ) {
        val call =
            api.getWarehouseDocumentList(request)
        call.enqueue(object : ApiCallback<WarehouseDocumentListResponse>(context) {
            override fun response(response: WarehouseDocumentListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun deleteWarehouseDocument(
        request: DeleteWarehouseDocumentRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteWarehouseDocument(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }
    /** #End Warehouse Document =================================================================**/
    //
    //
    //
    //
    //
    /** #Begin Warehouse Document Detail ========================================================**/
    fun getWarehouseDocumentDetailList(
        request: WarehouseDocumentDetailRequest,
        callback: OnApiCallback<WarehouseDocumentDetailListResponse>
    ) {
        val call = api.getWarehouseDocumentDetailList(request)
        call.enqueue(object : ApiCallback<WarehouseDocumentDetailListResponse>(context) {
            override fun response(response: WarehouseDocumentDetailListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getSearchGoodsList(
        request: SearchGoodsListRequest,
        callback: OnApiCallback<WHSGoodsListResponse>
    ) {
        val call = api.getSearchGoodsList(request)
        call.enqueue(object : ApiCallback<WHSGoodsListResponse>(context) {
            override fun response(response: WHSGoodsListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun insertDocumentDetail(
        request: PostDocumentDetailRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.insertDocumentDetail(
            request
        )
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun updateDocumentDetail(
        request: PostDocumentDetailRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.updateDocumentDetail(
            request
        )
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun deleteWarehouseDocumentDetail(
        request: DeleteWarehouseDocumentDetailRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteWarehouseDocumentDetail(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }
    /** #End Warehouse Document Detail ==========================================================**/
    //
    //
    //
    //
    //
    /** #Begin Warehouse Document Sub Detail =================================================== **/
    fun getWarehouseDocumentSubDetailList(
        request: WarehouseDocumentSubDetailRequest,
        callback: OnApiCallback<WarehouseDocumentSubDetailResponse>
    ) {
        val call = api.getWarehouseDocumentSubDetailList(request)
        call.enqueue(object : ApiCallback<WarehouseDocumentSubDetailResponse>(context) {
            override fun response(response: WarehouseDocumentSubDetailResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun insertDocumentSubDetail(
        request: PostDocumentSubDetailRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.insertDocumentSubDetail(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun deleteDocumentSubDetail(
        request: DeleteDocumentSubDetailRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.deleteDocumentSubDetail(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    /** #End Warehouse Document Sub Detail ===================================================== **/
    //
    //
    //
    //
    //
    /** #Begin Add Document ==================================================================== **/
    fun getPlaceList(
        request: PlaceRequest,
        callback: OnApiCallback<PlaceListResponse>
    ) {
        val call = api.getPlaceList(request)
        call.enqueue(object : ApiCallback<PlaceListResponse>(context) {
            override fun response(response: PlaceListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    fun getWorkOrderList(
        request: WorkOrderRequest,
        callback: OnApiCallback<WorkOrderResponse>
    ) {
        val call = api.getWorkOrderList(request)
        call.enqueue(object : ApiCallback<WorkOrderResponse>(context) {
            override fun response(response: WorkOrderResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getProjectList(
        request: ProjectListRequest,
        callback: OnApiCallback<ProjectListResponse>
    ) {
        val call = api.getProjectList(request)
        call.enqueue(object : ApiCallback<ProjectListResponse>(context) {
            override fun response(response: ProjectListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getCustomerSearchList(
        request: CustomerSearchListRequest,
        callback: OnApiCallback<CustomerSearchListResponse>
    ) {
        val call = api.getCustomerSearchList(request)
        call.enqueue(object : ApiCallback<CustomerSearchListResponse>(context) {
            override fun response(response: CustomerSearchListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getContract(
        request : ContractRequest,
        callback: OnApiCallback<ContractByCustomerIdResponse>
    ) {
        val call = api.getContract(request)
        call.enqueue(object : ApiCallback<ContractByCustomerIdResponse>(context) {
            override fun response(response: ContractByCustomerIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getDocumentNumber(
        request: DocumentNumberRequest,
        callback: OnApiCallback<WarehouseDocumentListResponse>
    ) {
        val call = api.getDocumentNumber(request)
        call.enqueue(object : ApiCallback<WarehouseDocumentListResponse>(context) {
            override fun response(response: WarehouseDocumentListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun insertWarehouseDocument(
        request: PostWarehouseDocumentRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.insertWarehouseDocument(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun updateWarehouseDocument(
        request: PostWarehouseDocumentRequest,
        callback: OnApiCallback<ResponseId>
    ) {
        val call = api.updateWarehouseDocument(request)
        call.enqueue(object : ApiCallback<ResponseId>(context) {
            override fun response(response: ResponseId, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getReserveWarehouseDocument(
        request: WarehouseDocumentListRequest,
        callback: OnApiCallback<WarehouseDocumentListResponse>
    ) {
        val call = api.getReserveWarehouseDocument(request)
        call.enqueue(object : ApiCallback<WarehouseDocumentListResponse>(context) {
            override fun response(response: WarehouseDocumentListResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getCustomerAccountByCustomerId(
        request: CustomerAccountRequest,
        callback: OnApiCallback<CustomerAccountByCustomerIdResponse>
    ) {
        val call = api.getCustomerAccountByCustomerId(request)
        call.enqueue(object : ApiCallback<CustomerAccountByCustomerIdResponse>(context) {
            override fun response(response: CustomerAccountByCustomerIdResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }

    fun getAccountingCode(
        request: AccountingCodeReqeust,
        callback: OnApiCallback<AccountingCodeResponse>
    ) {
        val call = api.getAccountingCode(request)
        call.enqueue(object : ApiCallback<AccountingCodeResponse>(context) {
            override fun response(response: AccountingCodeResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }


    /** #End Add Document ====================================================================== **/
    //
    //
    //
    //
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- Begin Base Information ------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin Base Information ================================================================ **/

    fun getCustomer(
        request: CustomerRequest,
        callback: OnApiCallback<CustomerResponse>
    ) {
        val call = api.getCustomer(request)
        call.enqueue(object : ApiCallback<CustomerResponse>(context) {
            override fun response(response: CustomerResponse, headers: Headers) {
                callback.onDataLoaded(response)
            }

            override fun failure(response: BaseResponse?) {
                response?.let {
                    handleError(it)
                    callback.onError(response)
                } ?: kotlin.run {
                    callback.onError(response)
                }
            }
        })
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---------------------------------- End Base Information --------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #End Base Information ================================================================== **/


















}