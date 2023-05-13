package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PostDocumentDetailRequest : Serializable{
    @SerializedName("whS_WddID")
    var wddId: Long?= null

    @SerializedName("whS_WdID_fk")
    var wdId: Long?= null

    @SerializedName("whS_GoodsID_fk")
    var goodsId: String?= null

    @SerializedName("acC_AcID_fk")
    private val acId = 0

    @SerializedName("buD_BdtID_fk")
    private val bdtId = 0

    @SerializedName("whS_WddSerialNo")
    var serialNo: String?= null

    @SerializedName("whS_WddAmount")
    var amount: Int?= null

    @SerializedName("whS_WddTotalPrice")
    private val totalPrice: Long = 0

    @SerializedName("whS_WddNote")
    private val note: String = ""

    @SerializedName("whS_WddDate")
    private val wddDate: String = ""

    @SerializedName("whS_WddRegisterDate")
    var registerDate: String?= null

    @SerializedName("whS_WddType")
    private val type = 0

    @SerializedName("whS_WddActive")
    private val active = 1

    @SerializedName("whS_WddStatus")
    private val status = 0

    @SerializedName("whS_WddDeleteDate")
    private val deleteDate: String = ""

    @SerializedName("acC_FinancialYearID")
    var financialYearId: Int?= null

    @SerializedName("tbL_UserID")
    var userId: Long?= null
}