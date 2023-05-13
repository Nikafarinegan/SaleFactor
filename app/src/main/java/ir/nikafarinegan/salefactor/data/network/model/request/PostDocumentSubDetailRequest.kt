package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseRequest

class PostDocumentSubDetailRequest: BaseRequest() {

    @SerializedName("whS_WdsdID")
    private val wdsdId: Long= 0

    @SerializedName("whS_WddID_fk")
    var wddId: Long?= null

    @SerializedName("whS_GoodsID_fk")
    var goodsId: String?= null

    @SerializedName("whS_WdsdStartSerialNo")
    var startSerialNum: String?= null

    @SerializedName("whS_WdsdEndSerialNo")
    var endSerialNum: String?= null

    @SerializedName("whS_WdsdReusable")
    var reusable: Int?= null

    @SerializedName("whS_WdsdRegisterDate")
    var registerDate: String?= null


    //============================================================================================//
    @SerializedName("whS_WdsdAmount")
    private val amount: Int= 0

    @SerializedName("whS_WdsdTotalPrice")
    private val price: Long = 0

    @SerializedName("whS_WdsdNote")
    private val note: String?= null

    @SerializedName("whS_WdsdType")
    private val type = 0

    @SerializedName("whS_WdsdActive")
    private val active = 1

    @SerializedName("whS_WdsdStatus")
    private val status = 0

    @SerializedName("whS_WdsdDeleteDate")
    private val deleteDate: String = ""

    @SerializedName("pmI_MicID_fk")
    private val micId: Long = 0








}