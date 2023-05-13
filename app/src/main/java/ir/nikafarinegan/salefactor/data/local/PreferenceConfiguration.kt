package ir.nikafarinegan.salefactor.data.local

import android.content.Context
import android.content.SharedPreferences
import ir.nikafarinegan.salefactor.utils.Const.KEY_ACCESS_TOKEN
import ir.nikafarinegan.salefactor.utils.Const.KEY_APP_VERSION
import ir.nikafarinegan.salefactor.utils.Const.KEY_DEVICE_MODEL
import ir.nikafarinegan.salefactor.utils.Const.KEY_END_DATE
import ir.nikafarinegan.salefactor.utils.Const.KEY_FINANCIAL_YEAR_ID
import ir.nikafarinegan.salefactor.utils.Const.KEY_IMEI
import ir.nikafarinegan.salefactor.utils.Const.KEY_LOG_OUT
import ir.nikafarinegan.salefactor.utils.Const.KEY_OFFLINE_MODE
import ir.nikafarinegan.salefactor.utils.Const.KEY_OS_VERSION
import ir.nikafarinegan.salefactor.utils.Const.KEY_PASSWORD
import ir.nikafarinegan.salefactor.utils.Const.KEY_PERSONNEL_ID
import ir.nikafarinegan.salefactor.utils.Const.KEY_PREFERENCE_NAME
import ir.nikafarinegan.salefactor.utils.Const.KEY_REMEMBER_ME
import ir.nikafarinegan.salefactor.utils.Const.KEY_START_DATE
import ir.nikafarinegan.salefactor.utils.Const.KEY_USER_FAMILY
import ir.nikafarinegan.salefactor.utils.Const.KEY_USER_ID
import ir.nikafarinegan.salefactor.utils.Const.KEY_USER_NAME
import ir.nikafarinegan.salefactor.utils.Const.KEY_USER_POSITION
import ir.nikafarinegan.salefactor.utils.Const.KEY_USER_THUMBNAIL

class PreferenceConfiguration(
    context: Context
) {
    private var pref: SharedPreferences =
        context.getSharedPreferences(KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    var isLogout: Boolean
        get() = pref.getBoolean(KEY_LOG_OUT, false)
        set(value){
            pref.edit().putBoolean(KEY_LOG_OUT, value).apply()
        }


    var isOfflineMode: Boolean
        get() = pref.getBoolean(KEY_OFFLINE_MODE, false)
        set(value){
            pref.edit().putBoolean(KEY_OFFLINE_MODE, value).apply()
        }

    var rememberMe: Boolean
        get() = pref.getBoolean(KEY_REMEMBER_ME, false)
        set(value){
            pref.edit().putBoolean(KEY_REMEMBER_ME, value).apply()
        }

    var accessToken: String
        get() = pref.getString(KEY_ACCESS_TOKEN, "")!!
        set(value) = pref.edit().putString(KEY_ACCESS_TOKEN, value).apply()

    var username: String
        get() = pref.getString(KEY_USER_NAME, "")!!
        set(value) {
            pref.edit().putString(KEY_USER_NAME, value).apply()
        }

    var password: String
        get() = pref.getString(KEY_PASSWORD, "")!!
        set(value) {
            pref.edit().putString(KEY_PASSWORD, value).apply()
        }

    var userThumbnail: String
        get() = pref.getString(KEY_USER_THUMBNAIL, "")!!
        set(value) {
            pref.edit().putString(KEY_USER_THUMBNAIL, value).apply()
        }

    var userFamily: String
        get() = pref.getString(KEY_USER_FAMILY, "")!!
        set(value) {
            pref.edit().putString(KEY_USER_FAMILY, value).apply()
        }

    var personnelId: Long
        get() = pref.getLong(KEY_PERSONNEL_ID, 0)
        set(value) {
            pref.edit().putLong(KEY_PERSONNEL_ID, value).apply()
        }

    var userId: Long
        get() = pref.getLong(KEY_USER_ID, 0)
        set(value) {
            pref.edit().putLong(KEY_USER_ID, value).apply()
        }

    var userPosition: String
        get() = pref.getString(KEY_USER_POSITION, "")!!
        set(value) {
            pref.edit().putString(KEY_USER_POSITION, value).apply()
        }


    var financialYearId: Int
        get() = pref.getInt(KEY_FINANCIAL_YEAR_ID, 0)
        set(value) {
            pref.edit().putInt(KEY_FINANCIAL_YEAR_ID, value).apply()
        }

    var startDate: String
        get() = pref.getString(KEY_START_DATE, " ")!!
        set(value) {
            pref.edit().putString(KEY_START_DATE, value).apply()
        }

    var endDate: String
        get() = pref.getString(KEY_END_DATE, " ")!!
        set(value) {
            pref.edit().putString(KEY_END_DATE, value).apply()
        }

    var imei: String
        get() = pref.getString(KEY_IMEI, "")!!
        set(value){
            pref.edit().putString(KEY_IMEI, value).apply()
        }

    var osVersion: String
        get() = pref.getString(KEY_OS_VERSION, "")!!
        set(value){
            pref.edit().putString(KEY_OS_VERSION, value).apply()
        }

    var deviceModel: String
        get() = pref.getString(KEY_DEVICE_MODEL, "")!!
        set(value){
            pref.edit().putString(KEY_DEVICE_MODEL, value).apply()
        }

    var appVersion: String
        get() = pref.getString(KEY_APP_VERSION, "")!!
        set(value) {
            pref.edit().putString(KEY_APP_VERSION, value).apply()
        }
}