package ir.nikafarinegan.salefactor.data.network.api

import ir.nikafarinegan.salefactor.data.network.WebServiceGateway

class ApiClient (
    private val gateway: WebServiceGateway
) {
    fun getInterface(): ApiInterface =
        gateway
        .retrofit
        .create(ApiInterface::class.java)
}