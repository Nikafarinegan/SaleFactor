package ir.nikafarinegan.salefactor.view.operation.document

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.*
import ir.nikafarinegan.salefactor.data.network.model.response.*
import ir.nikafarinegan.salefactor.view.base.BaseViewModel

class DocumentViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(pref, calendar) {

    val documentControlValidationResponse = MutableLiveData<WarehouseDocumentListResponse>()
    val documentDetailControlValidationResponse = MutableLiveData<WarehouseDocumentDetailListResponse>()
    val documentSubDetailControlValidationResponse = MutableLiveData<WarehouseDocumentSubDetailResponse>()


    val documentCodeResponse = MutableLiveData<WarehouseDocumentListResponse>()
    val warehouseListResponse = MutableLiveData<WarehouseListResponse>()
    val documentTypeResponse = MutableLiveData<DocumentTypeListResponse>()
    val warehouseDocumentListResponse = MutableLiveData<WarehouseDocumentListResponse>()
    val warehouseDocumentDetailResponse = MutableLiveData<WarehouseDocumentDetailListResponse>()
    val warehouseDocumentSubDetailResponse = MutableLiveData<WarehouseDocumentSubDetailResponse>()
    val placeListResponse = MutableLiveData<PlaceListResponse>()
    val workOrderResponse = MutableLiveData<WorkOrderResponse>()
    val projectResponse = MutableLiveData<ProjectListResponse>()
    val customerResponse = MutableLiveData<CustomerSearchListResponse>()
    val contractResponse = MutableLiveData<ContractByCustomerIdResponse>()
    val reserveWarehouseDocumentResponse = MutableLiveData<WarehouseDocumentListResponse>()
    val customerAccountByCustomerIdResponse = MutableLiveData<CustomerAccountByCustomerIdResponse>()
    val accountingCodeResponse = MutableLiveData<AccountingCodeResponse>()
    val searchGoodsResponse = MutableLiveData<WHSGoodsListResponse>()


    fun getWarehouseList(
        request: WarehouseListRequest
    ) {
        remote.getWarehouseList(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseListResponse> {
                override fun onDataLoaded(data: WarehouseListResponse) {
                    warehouseListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getDocumentTypeList(
        request: DocumentTypeListRequest
    ) {
        remote.getWarehouseDocumentTypeList(
            request,
            object : RemoteRepository.OnApiCallback<DocumentTypeListResponse> {
                override fun onDataLoaded(data: DocumentTypeListResponse) {
                    documentTypeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getWarehouseDocumentList(
        request: WarehouseDocumentListRequest
    ) {
        remote.getWarehouseDocumentList(
           request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentListResponse> {
                override fun onDataLoaded(data: WarehouseDocumentListResponse) {
                    warehouseDocumentListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun deleteWarehouseDocument(
        request: DeleteWarehouseDocumentRequest
    ) {
        remote.deleteWarehouseDocument(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorDelete.postValue(response)
                }
            })
    }

    fun documentControlValidation(
        request: WarehouseDocumentListRequest
    ) {
        remote.getWarehouseDocumentList(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentListResponse> {
                override fun onDataLoaded(data: WarehouseDocumentListResponse) {
                    documentControlValidationResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
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
    fun documentDetailControlValidation(
        request: WarehouseDocumentDetailRequest
    ){
        remote.getWarehouseDocumentDetailList(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentDetailListResponse> {
                override fun onDataLoaded(data: WarehouseDocumentDetailListResponse) {
                    documentDetailControlValidationResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getWarehouseDocumentDetailList(
        request: WarehouseDocumentDetailRequest
    ) {
        remote.getWarehouseDocumentDetailList(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentDetailListResponse> {
                override fun onDataLoaded(data: WarehouseDocumentDetailListResponse) {
                    warehouseDocumentDetailResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getSearchGoodsList(
        request: SearchGoodsListRequest
    ){
        remote.getSearchGoodsList(
            request,
            object : RemoteRepository.OnApiCallback<WHSGoodsListResponse>{
                override fun onDataLoaded(data: WHSGoodsListResponse) {
                    searchGoodsResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun insertDocumentDetail(
        request: PostDocumentDetailRequest
    ){
        remote.insertDocumentDetail(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId>{
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun updateDocumentDetail(
        request: PostDocumentDetailRequest
    ){
        remote.updateDocumentDetail(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId>{
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun deleteWarehouseDocumentDetail(
        request: DeleteWarehouseDocumentDetailRequest
    ) {
        remote.deleteWarehouseDocumentDetail(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorDelete.postValue(response)
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
    fun subDetailControlValidation(
        request: WarehouseDocumentSubDetailRequest
    ) {
        remote.getWarehouseDocumentSubDetailList(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentSubDetailResponse> {
                override fun onDataLoaded(data: WarehouseDocumentSubDetailResponse) {
                    documentSubDetailControlValidationResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getWarehouseDocumentSubDetailList(
        request: WarehouseDocumentSubDetailRequest
    ) {
        remote.getWarehouseDocumentSubDetailList(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentSubDetailResponse> {
                override fun onDataLoaded(data: WarehouseDocumentSubDetailResponse) {
                    warehouseDocumentSubDetailResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun deleteDocumentSubDetail(
        request: DeleteDocumentSubDetailRequest
    ) {
        remote.deleteDocumentSubDetail(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    errorDelete.postValue(response)
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
        request: PlaceRequest
    ) {
        remote.getPlaceList(
            request,
            object : RemoteRepository.OnApiCallback<PlaceListResponse> {
                override fun onDataLoaded(data: PlaceListResponse) {
                    placeListResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getWorkOrderList(
        request: WorkOrderRequest
    ) {
        remote.getWorkOrderList(
          request,
            object : RemoteRepository.OnApiCallback<WorkOrderResponse> {
                override fun onDataLoaded(data: WorkOrderResponse) {
                    workOrderResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getProjectList(
        request: ProjectListRequest
    ) {
        remote.getProjectList(
          request,
            object : RemoteRepository.OnApiCallback<ProjectListResponse> {
                override fun onDataLoaded(data: ProjectListResponse) {
                    projectResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getCustomerSearchList(
        request: CustomerSearchListRequest
    ) {
        remote.getCustomerSearchList(
            request,
            object : RemoteRepository.OnApiCallback<CustomerSearchListResponse> {
                override fun onDataLoaded(data: CustomerSearchListResponse) {
                    customerResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getContract(
        request : ContractRequest
    ) {
        remote.getContract(
            request,
            object : RemoteRepository.OnApiCallback<ContractByCustomerIdResponse> {
                override fun onDataLoaded(data: ContractByCustomerIdResponse) {
                    contractResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getDocumentNumber(
        request: DocumentNumberRequest
    ) {
        remote.getDocumentNumber(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentListResponse> {
                override fun onDataLoaded(data: WarehouseDocumentListResponse) {
                    documentCodeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun insertWarehouseDocument(
        request: PostWarehouseDocumentRequest
    ) {
        remote.insertWarehouseDocument(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun updateWarehouseDocument(
        request: PostWarehouseDocumentRequest
    ) {
        remote.updateWarehouseDocument(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getReserveWarehouseDocument(
        request: WarehouseDocumentListRequest
    ) {
        remote.getReserveWarehouseDocument(
            request,
            object : RemoteRepository.OnApiCallback<WarehouseDocumentListResponse> {
                override fun onDataLoaded(data: WarehouseDocumentListResponse) {
                    reserveWarehouseDocumentResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getCustomerAccountByCustomerId(
        request: CustomerAccountRequest
    ) {
        remote.getCustomerAccountByCustomerId(
            request,
            object : RemoteRepository.OnApiCallback<CustomerAccountByCustomerIdResponse> {
                override fun onDataLoaded(data: CustomerAccountByCustomerIdResponse) {
                    customerAccountByCustomerIdResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getAccountingCode(
        request: AccountingCodeReqeust
    ) {
        remote.getAccountingCode(
            request,
            object : RemoteRepository.OnApiCallback<AccountingCodeResponse> {
                override fun onDataLoaded(data: AccountingCodeResponse) {
                    accountingCodeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }


    /** #End Add Document ====================================================================== **/

}