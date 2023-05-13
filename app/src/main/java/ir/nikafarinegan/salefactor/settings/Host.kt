package ir.nikafarinegan.salefactor.settings

import ir.nikafarinegan.salefactor.settings.URL.HOST_8014


val HOST_NAME: String = HOST_8014.first
val APP_TITLE: String = HOST_8014.second


internal object URL {
    const val URL_8014: String = "http://185.79.156.176:8014/api/"
    const val APP_TITLE_8014 = "توزیع برق منطقه ای سمنان"

    const val URL_TEHRAN = "http://185.79.156.176/WebAPIAndroid-Tehran/api/"
    const val APP_TITLE_TEHRAN = "شرکت توزیع نیروی برق تهران بزرگ"

    const val URL_ISFAHAN_TOWN = "http://185.79.156.176:8015/api/"
//    const val URL_ISFAHAN_TOWN = "http://185.79.156.176/webapiandroid-shahrestan/api/"
    const val APP_TITLE_ISFAHAN_TOWN = "شرکت توزیع نیروی برق شهرستان اصفهان"

    const val URL_TAVANIR = "http://185.79.156.176/webAPIAndroid-Tavanir/api/" //tavanir-server
    const val APP_TITLE_TAVANIR = "شرکت توانیر"

    val HOST_8014 = Pair(URL_8014, APP_TITLE_8014)

    val HOST_TEHRAN = Pair(URL_TEHRAN, APP_TITLE_TEHRAN)

    val HOST_TAVANIR = Pair(URL_TAVANIR, APP_TITLE_TAVANIR)

    val HOST_ISFAHAN = Pair(URL_ISFAHAN_TOWN, APP_TITLE_ISFAHAN_TOWN)
}