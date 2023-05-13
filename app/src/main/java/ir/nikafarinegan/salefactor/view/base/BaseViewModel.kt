package ir.nikafarinegan.salefactor.view.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.formatDate
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.awlrhm.modules.view.changesDialog.ReleaseChangeModel
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.data.network.RemoteRepository
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.data.network.model.request.PostUnhandledExceptionRequest
import ir.nikafarinegan.salefactor.data.network.model.response.ResponseBoolean
import ir.nikafarinegan.salefactor.data.network.model.response.ResponseId
import ir.nikafarinegan.salefactor.settings.listChanges

open class BaseViewModel() : ViewModel() {

    private lateinit var pref: PreferenceConfiguration
    private lateinit var calendar: PersianCalendar
    private lateinit var remote: RemoteRepository

    constructor(
        pref: PreferenceConfiguration,
        calendar: PersianCalendar,
        remote: RemoteRepository
    ) : this() {
        this.calendar = calendar
        this.pref = pref
        this.remote = remote
    }

    constructor(
        pref: PreferenceConfiguration,
        calendar: PersianCalendar
    ) : this() {
        this.calendar = calendar
        this.pref = pref
    }

    constructor(
        pref: PreferenceConfiguration,
        remote: RemoteRepository
    ) : this() {
        this.remote = remote
        this.pref = pref
    }

    constructor(
        remote: RemoteRepository,
        calendar: PersianCalendar
    ) : this() {
        this.remote = remote
        this.calendar = calendar
    }

    constructor(pref: PreferenceConfiguration) : this() {
        this.pref = pref
    }

    constructor(remote: RemoteRepository) : this() {
        this.remote = remote
    }



    val error = MutableLiveData<BaseResponse>()
    val errorDelete = MutableLiveData<BaseResponse>()
    val responseId = MutableLiveData<ResponseId>()
    val responseBoolean = MutableLiveData<ResponseBoolean>()
    val errorUnhandledException = MutableLiveData<BaseResponse?>()


    fun getChangesList() : List<ReleaseChangeModel>{
        return listChanges
    }

    fun checkConnection(context: Context, callback: () -> Unit) {
        if (isOfflineMode) {
            context.yToast(context.getString(R.string.no_internet), MessageStatus.ERROR)
        } else
            callback.invoke()
    }

    fun postUnhandledException(
        request: PostUnhandledExceptionRequest
    ) {
        remote.postUnhandledException(
            request,
            object : RemoteRepository.OnApiCallback<ResponseId> {
                override fun onDataLoaded(data: ResponseId) {
                    responseId.postValue(data)
                }

                override fun onError(response: BaseResponse?) {
                    response?.let {
                        errorUnhandledException.postValue(it)
                    }
                }
            })
    }


    // base functions and variables =====================================================
    fun clearCash() {
        startDate = "${currentDate.split('/')[0]}/01/01"
        endDate = currentDate
        accessToken = ""
        userThumbnail = ""
        userFamily = ""
        userPosition = ""
        personnelId = 0
        userId = 0
        isLogout = false
    }

    var isLogout: Boolean
        get() = pref.isLogout
        set(value){
            pref.isLogout = value
        }

    var isOfflineMode: Boolean
        get() = pref.isOfflineMode
        set(value){
            pref.isOfflineMode = value
        }


    var rememberMe: Boolean
        get() = pref.rememberMe
        set(value) {
            pref.rememberMe = value
        }

    val currentDate: String
        get() = formatDate(calendar.persianShortDate)


    val registerDate: String
    get() = calendar.persianShortDateTime

    var startDate: String
        get() = pref.startDate
        set(value) {
            pref.startDate = value
        }

    var endDate: String
        get() = pref.endDate
        set(value) {
            pref.endDate = value
        }

    var accessToken: String
        get() = pref.accessToken
        set(value) {
            pref.accessToken = value
        }

    var username: String
        get() = pref.username
        set(value) {
            pref.username = value
        }

    var password: String
        get() = pref.password
        set(value) {
            pref.password = value
        }

    var userThumbnail: String
        get() = pref.userThumbnail
        set(value) {
            pref.userThumbnail = value
        }

    var userFamily: String
        get() = pref.userFamily
        set(value) {
            pref.userFamily = value
        }

    var userPosition: String
        get() = pref.userPosition
        set(value) {
            pref.userPosition = value
        }

    var personnelId: Long
        get() = pref.personnelId
        set(value) {
            pref.personnelId = value
        }

    var userId: Long
        get() = pref.userId
        set(value) {
            pref.userId = value
        }

    var financialYearId: Int
        get(){
            return if(pref.financialYearId == 0)
                calendar.persianYear
            else pref.financialYearId
        }
        set(value) {
            pref.financialYearId = value
        }

    var imei: String
        get() = pref.imei
        set(value) {
            pref.imei = value
        }

    var osVersion: String
        get() = pref.osVersion
        set(value) {
            pref.osVersion = value
        }

    var deviceModel: String
        get() = pref.deviceModel
        set(value) {
            pref.deviceModel = value
        }


    var appVersion: String
        get() = pref.appVersion
        set(value) {
            pref.appVersion = value
        }
}