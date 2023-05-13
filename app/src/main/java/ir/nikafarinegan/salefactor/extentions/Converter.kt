package ir.nikafarinegan.salefactor.extentions

import androidx.lifecycle.liveData
import com.google.gson.GsonBuilder
import ir.nikafarinegan.salefactor.data.network.model.response.CustomerResponse
import ir.nikafarinegan.salefactor.data.network.model.response.SubSystemResponse

fun convertJsonSubSystemToModel(json: String) =
    liveData {
        emit(
            try {
                GsonBuilder()
                    .create()
                    .fromJson(json, Array<SubSystemResponse.Result>::class.java)
                    .toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }
        )
    }


fun convertJsonCustomerToModel(json: String) =
    liveData {
        emit(
            try {
                GsonBuilder()
                    .create()
                    .fromJson(json, Array<CustomerResponse.Result>::class.java)
                    .toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }
        )
    }
