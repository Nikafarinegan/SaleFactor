package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 25/10/2021.
 */
class FinancialYearRequest: BaseGetRequest(){
    @SerializedName("acC_AcID")
    private val acId: Long = 0
}