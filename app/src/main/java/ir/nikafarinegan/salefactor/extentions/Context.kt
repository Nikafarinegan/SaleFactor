package ir.nikafarinegan.salefactor.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}

fun Context.lastUpdateDate(date: String?): String{
    return date?.let{
        it
    }?: run {
        getString(R.string.no_date)
    }
}


fun getUUID() = UUID.randomUUID().toString()

@SuppressLint("SimpleDateFormat")
fun getTimestamp(
    day: Int,
    month: Int,
    year: Int
): Long {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val strDate = "$month-$day-$year"
        val formatter: DateFormat =  SimpleDateFormat("MM-dd-yyyy")
        val date = formatter.parse(strDate) as Date
        val output = date.time / 1000L
        val str = output.toString()
        return str.toLong() * 1000
    } else {
        val tsLong = System.currentTimeMillis() / 1000
        return tsLong
    }

}