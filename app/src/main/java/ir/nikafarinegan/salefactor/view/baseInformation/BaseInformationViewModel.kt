package ir.nikafarinegan.salefactor.view.baseInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ir.awlrhm.modules.extentions.convertModelToJson
import ir.nikafarinegan.salefactor.data.database.LocalRepository
import ir.nikafarinegan.salefactor.data.database.entity.Customer
import ir.nikafarinegan.salefactor.data.database.entity.SubSystem
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.CustomerRequest
import ir.nikafarinegan.salefactor.data.network.model.response.CustomerResponse
import ir.nikafarinegan.salefactor.extentions.convertJsonCustomerToModel
import ir.nikafarinegan.salefactor.view.base.BaseViewModel
import kotlinx.coroutines.launch

class BaseInformationViewModel(
    private val remote: RemoteRepository,
    private val repository: LocalRepository,
    pref: PreferenceConfiguration
): BaseViewModel(pref, remote) {


    val customerResponse = MutableLiveData<CustomerResponse>()

    fun getCustomer(
        request: CustomerRequest
    ) {
        remote.getCustomer(
            request,
            object : RemoteRepository.OnApiCallback<CustomerResponse> {
                override fun onDataLoaded(data: CustomerResponse) {
                    data.result?.let { list ->
                        if (list.isNotEmpty()) {
                            deleteCustomer()
                            insertCustomer(
                                Customer().apply {
                                    this.xJson = convertModelToJson(list)
                                    this.xUpdateDate = data.dateTime
                                }
                            )
                        }
                    }
                    customerResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }




    /////////////////////////////////////////////////////////////////////////////////////////////////

    //----------------------------------- DataBase -------------------------------------------------

    /////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin Customer =========================================================================**/

    fun getCustomerDb() = repository.getCustomers()

    fun getCustomerDb(json: String?):
            LiveData<MutableList<CustomerResponse.Result>> =
        liveData {
            val list = convertJsonCustomerToModel(json ?: "")
            emitSource(list)
        }


    fun insertCustomer(customer: Customer){
        viewModelScope.launch {
            repository.insertCustomer(customer)
        }
    }

    fun deleteCustomer(){
        viewModelScope.launch {
            repository.deleteCustomer()
        }
    }


    /** #End Customer ===========================================================================**/


}