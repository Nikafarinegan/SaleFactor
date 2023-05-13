package ir.nikafarinegan.salefactor.data.network

import android.content.Context
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.copyFile
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.security.PeerCertificateExtractor
import ir.awlrhm.modules.utils.calendar.PersianCalendar
import ir.nikafarinegan.salefactor.BuildConfig
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.local.PreferenceConfiguration
import ir.nikafarinegan.salefactor.extentions.getTimestamp
import ir.nikafarinegan.salefactor.extentions.getUUID
import ir.nikafarinegan.salefactor.settings.BASE_URL
import ir.nikafarinegan.salefactor.settings.CERTIFICATE_NAME
import ir.nikafarinegan.salefactor.settings.HOST_NAME
import ir.nikafarinegan.salefactor.settings.PATH
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.TimeUnit

class WebServiceGateway(
    private val pref: PreferenceConfiguration,
    private val context: Context,
    private val calendar: PersianCalendar
) {
    val retrofit: Retrofit
        get() {
            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    connectTimeout(300, TimeUnit.SECONDS)
                    readTimeout(300, TimeUnit.SECONDS)
                    writeTimeout(300, TimeUnit.SECONDS)
                } else {
                    connectTimeout(180, TimeUnit.SECONDS)
                    readTimeout(180, TimeUnit.SECONDS)
                    writeTimeout(180, TimeUnit.SECONDS)
                }
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)

                addInterceptor(Interceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${pref.accessToken}")
                        .addHeader("requestTraceId", getUUID())
                        .addHeader(
                            "timestamp",
                            getTimestamp(
                                calendar.persianDay,
                                calendar.persianMonth,
                                calendar.persianYear
                            ).toString()
                        )
                        .build()
                    chain.proceed(newRequest)
                })

                val certificateFile = File(PATH + File.separator + CERTIFICATE_NAME)
                if (certificateFile.exists()) {
                    val cert: InputStream = FileInputStream(certificateFile)
                    val certificate = File.createTempFile("prefix", "suffix")
                    copyFile(cert, FileOutputStream(certificate))

                    val peerCertificate: String =
                        PeerCertificateExtractor.extract(certificate)
                    val certificatePinner = CertificatePinner.Builder()
                        .add(HOST_NAME, peerCertificate)
                        .build()
                    certificatePinner(certificatePinner)
                }else
                    context.showLogin()
            }.build()

            return Retrofit.Builder().apply {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl(BASE_URL)
                client(client)
            }.build()
        }

    private fun Context.showLogin() {
        yToast(
            getString(R.string.error_certificate_file),
            MessageStatus.ERROR
        )
    }

}