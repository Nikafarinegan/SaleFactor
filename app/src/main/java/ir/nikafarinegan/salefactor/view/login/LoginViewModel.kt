package ir.nikafarinegan.salefactor.view.login

import androidx.lifecycle.MutableLiveData
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.LoginRequest
import ir.nikafarinegan.salefactor.data.network.model.response.LoginResponse
import ir.nikafarinegan.salefactor.data.network.model.response.ResponseId
import ir.nikafarinegan.salefactor.utils.ErrorKey
import ir.nikafarinegan.salefactor.view.base.BaseViewModel

class LoginViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
): BaseViewModel(pref, calendar, remote) {

    val loginResponse = MutableLiveData<LoginResponse>()
    val passwordResponse = MutableLiveData<ResponseId>()
    val downloadVersionResponse = MutableLiveData<LoginResponse>()


    fun login(
        userName: String,
        password: String
    ) {
        remote.login(
            LoginRequest(userName, password),
            object : RemoteRepository.OnApiCallback<LoginResponse> {
                override fun onDataLoaded(data: LoginResponse) {
                    loginResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let {
                        if (it.statusDescription == ErrorKey.DOWNLOAD_VERSION)
                            downloadVersionResponse.postValue(response as LoginResponse)
                        error.postValue(it)
                    }
                }
            })
    }

    fun postPasswordRecover(
        request: BaseGetRequest
    ){
        remote.postPasswordRecover(
            request,
            object: RemoteRepository.OnApiCallback<ResponseId>{
                override fun onDataLoaded(data: ResponseId) {
                    passwordResponse.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let { error.postValue(it) }
                }
            })
    }
}