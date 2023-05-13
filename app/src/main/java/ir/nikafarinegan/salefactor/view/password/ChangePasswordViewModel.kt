package ir.nikafarinegan.salefactor.view.password

import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.response.ResponseId
import ir.nikafarinegan.salefactor.view.base.BaseViewModel

class ChangePasswordViewModel(
    pref: PreferenceConfiguration,
    private val remote: RemoteRepository
): BaseViewModel(pref, remote) {

    fun postChangePassword(request: BaseGetRequest){
        remote.postChangePassword(
            request,
            object: RemoteRepository.OnApiCallback<ResponseId>{
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }
}