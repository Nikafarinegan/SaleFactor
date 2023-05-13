package ir.nikafarinegan.salefactor.view.register

import androidx.lifecycle.MutableLiveData
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.UserSmartDeviceRequest
import ir.nikafarinegan.salefactor.data.network.model.response.PersonnelResponse
import ir.nikafarinegan.salefactor.data.network.model.response.ResponseId
import ir.nikafarinegan.salefactor.data.network.model.response.UserSmartDeviceResponse
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.view.base.BaseViewModel

class RegisterViewModel(
    pref: PreferenceConfiguration,
    private val remote: RemoteRepository
): BaseViewModel(pref) {

    val confirmCodeError = MutableLiveData<BaseResponse>()
    val checkConfirmCodeResponse = MutableLiveData<ResponseId>()
    val userSmartDeviceByIMEIResponse = MutableLiveData<UserSmartDeviceResponse>()
    val personnelResponse = MutableLiveData<PersonnelResponse.Result>()
    val userSmartDeviceErrorResponse = MutableLiveData<BaseResponse>()

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
                            userId = model.userId ?: 0
                            personnelResponse.postValue(model)
                        }
                    }
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

    fun isExistUserSmartDevice(
        request: UserSmartDeviceRequest
    ) {
        remote.isExistUserSmartDevice(
            request,
            object : RemoteRepository.OnApiCallback<UserSmartDeviceResponse> {
                override fun onDataLoaded(data: UserSmartDeviceResponse) {
                    userSmartDeviceByIMEIResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let {
                        userSmartDeviceErrorResponse.postValue(it)
                    }
                }
            })
    }

    fun getCheckConfirmCode(
        request: UserSmartDeviceRequest
    ) {
        remote.getCheckConfirmCode(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    checkConfirmCodeResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let {
                        confirmCodeError.postValue(it)
                    }
                }
            })
    }

    fun postUserSmartDevice(
        request: UserSmartDeviceRequest
    ) {
        remote.postUserSmartDevice(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { error.postValue(it) }
                }
            })
    }
}