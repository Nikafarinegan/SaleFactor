package ir.nikafarinegan.salefactor.view.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import ir.awlrhm.modules.extentions.convertModelToJson
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nikafarinegan.salefactor.data.database.LocalRepository
import ir.nikafarinegan.salefactor.data.database.entity.SubSystem
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.FinancialYearRequest
import ir.nikafarinegan.salefactor.data.network.model.request.UserSmartDeviceRequest
import ir.nikafarinegan.salefactor.data.network.model.response.FinancialYearResponse
import ir.nikafarinegan.salefactor.data.network.model.response.PersonnelResponse
import ir.nikafarinegan.salefactor.data.network.model.response.SubSystemResponse
import ir.nikafarinegan.salefactor.data.network.model.response.UserSmartDeviceResponse
import ir.nikafarinegan.salefactor.extentions.convertJsonSubSystemToModel
import ir.nikafarinegan.salefactor.view.base.BaseViewModel
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val remote: RemoteRepository,
    private val repository: LocalRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
) : BaseViewModel(pref, calendar, remote) {

    val subSystemResponse = MutableLiveData<SubSystemResponse>()
    val financialYearResponse = MutableLiveData<FinancialYearResponse>()
    val personnelResponse = MutableLiveData<Boolean>()
    val userSmartDeviceResponse = MutableLiveData<UserSmartDeviceResponse>()


    fun getPersonnelInformation(
        request: BaseGetRequest
    ) {
        remote.getPersonnelInformation(
            request,
            object : RemoteRepository.OnApiCallback<PersonnelResponse> {
                override fun onDataLoaded(data: PersonnelResponse) {
                    data.result?.let { list ->
                        list[0].also { model ->
                            personnelId = model.personnelId ?: 0
                            userThumbnail = model.thumbnail ?: ""
                            userFamily = "${model.personnelNameTitle}"
                            userPosition = model.postTitle ?: ""
                            personnelResponse.postValue(true)
                        }
                    } ?: kotlin.run {
                        personnelResponse.postValue(false)
                    }
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }


    fun getUserSmartDevice(
        request: UserSmartDeviceRequest
    ) {
        remote.getUserSmartDevice(
            request,
            object : RemoteRepository.OnApiCallback<UserSmartDeviceResponse> {
                override fun onDataLoaded(data: UserSmartDeviceResponse) {
                    userSmartDeviceResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getSubSystemList(
        subSystemId: Int,
        request: BaseGetRequest
    ) {
        remote.getSubSystemList(
            request,
            object : RemoteRepository.OnApiCallback<SubSystemResponse> {
                override fun onDataLoaded(data: SubSystemResponse) {
                    data.result?.let { list ->
                        if (list.isNotEmpty()) {
                            deleteSubSystem(subSystemId)
                            insertSubSystem(
                                SubSystem().apply {
                                    this.xSsId = subSystemId
                                    this.xJson = convertModelToJson(list)
                                    this.xUpdateDate = data.dateTime
                                }
                            )
                        }
                    }
                    subSystemResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun getFinancialYearList(
        request: FinancialYearRequest
    ) {
        remote.getFinancialYearList(
            request,
            object : RemoteRepository.OnApiCallback<FinancialYearResponse> {
                override fun onDataLoaded(data: FinancialYearResponse) {
                    financialYearResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            }
        )
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    //-------------------------------------- Database ----------------------------------------------

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** #Begin SubSystem ======================================================================= **/
    fun getSubSystemDb(subSystemId: Int) = repository.getSubSystem(subSystemId)

    fun getSubSystemDb(json: String?):
            LiveData<MutableList<SubSystemResponse.Result>> =
        liveData {
            val list = convertJsonSubSystemToModel(json ?: "")
            emitSource(list)
        }

    fun insertSubSystem(model: SubSystem) {
        viewModelScope.launch {
            repository.insertSubSystem(model)
        }
    }

    fun deleteSubSystem(subSystemId: Int) {
        viewModelScope.launch {
            repository.deleteSubSystem(subSystemId)
        }
    }


    /** #End SubSystem ========================================================================= **/

}