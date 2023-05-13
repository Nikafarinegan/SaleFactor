package ir.nikafarinegan.salefactor.data.network.model.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Ali_Kazemi on 26/10/2021.
 */
open class BasePostRequest: Serializable {

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null
}