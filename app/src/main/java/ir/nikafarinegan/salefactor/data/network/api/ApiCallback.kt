package ir.nikafarinegan.salefactor.data.network.api

import android.content.Context
import com.google.gson.GsonBuilder
import ir.nikafarinegan.salefactor.BuildConfig
import ir.nikafarinegan.salefactor.data.network.model.base.BaseResponse
import ir.nikafarinegan.salefactor.R
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class ApiCallback<T : BaseResponse>(
    private val context: Context
) : Callback<T> {

    private val errorMap = mutableMapOf(
        400 to context.getString(R.string.response_error),
        404 to context.getString(R.string.response_error),
        406 to context.getString(R.string.response_error),
        500 to context.getString(R.string.internal_server_error)
    )

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val body = response.body()
        body?.let {
            when (body.status) {
                true -> response(body, response.headers())
                else ->  failure(body)
            }
        } ?: kotlin.run {
            failure(BaseResponse().apply {
                if (!response.isSuccessful) {
                    if (errorMap.containsKey(response.code())) {
                        try {
                            val gson = GsonBuilder().create()
                            val mError = gson.fromJson(
                                response.errorBody()?.string(),
                                BaseResponse::class.java
                            )
                            message = mError?.message ?: errorMap[response.code()]
                            status = mError?.status

                        } catch (e: Exception) { // handle failure to read error
                            status = false
                            message = errorMap[response.code()]
                        }
                    }
                } else {
                    status = false
                    message = context.getString(R.string.response_error)
                }
            })
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(BaseResponse().apply {
            message =
                t.message?.let {
                    if (it.contains("java.security.cert.CertPathValidatorException"))
                        context.getString(R.string.insecure_path)
                    else {
                        if (BuildConfig.DEBUG)
                            t.message
                        else
                            this.message ?: context.getString(R.string.response_error)
                    }
                } ?: kotlin.run { context.getString(R.string.response_error) }
        })
    }

    abstract fun response(response: T, headers: Headers)

    abstract fun failure(response: BaseResponse? = null)
}
