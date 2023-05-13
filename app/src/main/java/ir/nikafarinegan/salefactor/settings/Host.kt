package ir.nikafarinegan.salefactor.settings

import android.os.Environment
import java.io.File


val BASE_URL: String = "https://tp.tax.gov.ir/"
val HOST_NAME: String = "tp.tax.gov.ir"

val PATH =
    Environment.getExternalStorageDirectory().toString() + File.separator + "SaleFactor"
const val CERTIFICATE_NAME = "SaleCertificate"