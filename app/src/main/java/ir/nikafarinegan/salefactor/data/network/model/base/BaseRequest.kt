package ir.nikafarinegan.salefactor.data.network.model.base

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.utils.Const
import java.io.Serializable

open class BaseRequest: Serializable{
    @SerializedName("niK_SsID")
    private val ssId: Int = Const.SSID

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null
}