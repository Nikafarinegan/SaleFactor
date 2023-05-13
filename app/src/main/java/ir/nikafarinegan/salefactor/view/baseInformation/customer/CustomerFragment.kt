package ir.nikafarinegan.salefactor.view.baseInformation.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.showOfflineMessage
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.CustomerRequest
import ir.nikafarinegan.salefactor.data.network.model.response.CustomerResponse
import ir.nikafarinegan.salefactor.extentions.lastUpdateDate
import ir.nikafarinegan.salefactor.extentions.searchJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.baseInformation.BaseInformationViewModel
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.layout_last_update.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomerFragment(
    private val subSystemName: String?
) : BaseFragment() {

    private val viewModel by viewModel<BaseInformationViewModel>()
    private var pageNumber: Int = 1

    override fun setup() {
        txtTitle.text = subSystemName
        rclCustomer.layoutManager(LinearLayoutManager(activity))

        if (viewModel.isOfflineMode) {
            layoutUpdate.isVisible = true

            btnUpdate.setOnClickListener {
                logout()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }


    override fun onResume() {
        super.onResume()
        if(!rclCustomer.isOnLoading){
            rclCustomer.showLoading()
        }
        getItems()
    }


    override fun handleObservers() {
        viewModel.customerResponse.observe(viewLifecycleOwner, {
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                   setAdapter(list)

                } ?: kotlin.run {
                    rclCustomer.showNoData()
                }
            }
        })
    }

    override fun handleOnClickListeners() {
        btnBack.setOnClickListener { activity?.onBackPressed() }
    }

    private fun getItems(){
        val activity = activity ?: return

        if(viewModel.isOfflineMode){
            viewModel.getCustomerDb().observe(viewLifecycleOwner, {
                try {
                    txtLastUpdateDate.text = activity.lastUpdateDate(it.xUpdateDate)
                    convertJsonToModel(it.xJson)

                }catch (e: Exception){
                    rclCustomer.showNoData()
                    txtLastUpdateDate.text = getString(R.string.no_date)
                    activity.showOfflineMessage()
                }
            })
        }else{
            getCustomerList("")
        }
    }

    private fun getCustomerList(search: String = "") {
        viewModel.getCustomer(
            CustomerRequest().also { request ->
                request.customerId = 0
                request.typeOperation = 101
                request.jsonParameters = searchJson(search)
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
            }
        )
    }


    private fun convertJsonToModel(json: String?) {
        viewModel.getCustomerDb(json).observe(viewLifecycleOwner, { list ->
            setAdapter(list)
        })
    }

    private fun setAdapter(list: MutableList<CustomerResponse.Result>){
        if (list.isEmpty())
            rclCustomer.showNoData()
        else {
            rclCustomer.view?.adapter = Adapter(list)
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclCustomer.showNoData()
                activity?.showError(it?.message)
            }
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${CustomerFragment::class.java.simpleName}"
    }
}