package ir.nikafarinegan.salefactor.view.operation.subGoods

import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.PostDocumentSubDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.response.ResponseId
import ir.nikafarinegan.salefactor.view.base.BaseViewModel

class SubGoodsViewModel(
    private val remote: RemoteRepository,
    pref: PreferenceConfiguration,
    calendar: PersianCalendar
): BaseViewModel(pref, calendar) {


    fun insertDocumentSubDetail(
        request: PostDocumentSubDetailRequest
    ) {
        remote.insertDocumentSubDetail(
            request ,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    error.postValue(response)
                }
            })
    }

}