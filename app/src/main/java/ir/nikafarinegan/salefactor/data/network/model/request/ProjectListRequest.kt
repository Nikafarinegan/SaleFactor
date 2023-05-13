package ir.nikafarinegan.salefactor.data.network.model.request

import com.google.gson.annotations.SerializedName
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest

/**
 * Created by Ali_Kazemi on 26/10/2021.
 */
class ProjectListRequest: BaseGetRequest() {
    @SerializedName("buD_ProjectID")
    private val projectId: Long = 0
}