package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PostUnhandledExceptionRequest: Serializable {
    @SerializedName("niK_UeStackTrace")
    var stackTrace: String?= ""

    @SerializedName("niK_UeReasonOfCrash")
    var reasonOfCrash: String?= ""

    @SerializedName("niK_UeFormPlace")
    var formPlace: String?= ""

    @SerializedName("niK_UeOccurreDateTime")
    var dateTime: String?= ""

    @SerializedName("niK_UeRegisterDate")
    var registerDate: String?= ""

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null

    @SerializedName("niK_UeNote")
    private val note: String = ""

    @SerializedName("niK_UeType")
    private val type: Int = 0

    @SerializedName("niK_UeStatus")
    private val status: Int = 0

    @SerializedName("niK_UeActive")
    private val active: Int = 1

    @SerializedName("niK_UeDeleteDate")
    private val deleteDate: String = ""

    @SerializedName("niK_UeID")
    private val id: Int = 0
}