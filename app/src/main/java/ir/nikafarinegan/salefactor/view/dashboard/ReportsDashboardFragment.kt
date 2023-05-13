package ir.nikafarinegan.salefactor.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.showOfflineMessage
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.data.network.model.response.SubSystemResponse
import ir.nikafarinegan.salefactor.extentions.lastUpdateDate
import ir.nikafarinegan.salefactor.extentions.subSystemJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.dashboard.item.ItemsAdapter
import kotlinx.android.synthetic.main.fragment_dashboard_tab.*
import kotlinx.android.synthetic.main.layout_last_update.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportsDashboardFragment: BaseFragment() {

    private val viewModel by viewModel<DashboardViewModel>()

    override fun setup() {
        if (viewModel.isOfflineMode) {
            layoutUpdate.isVisible = true

            btnUpdate.setOnClickListener {
                logout()
            }
        }

        rclDashboardTab.layoutManager(
            GridLayoutManager(activity, 3)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard_tab, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!rclDashboardTab.isOnLoading)
            rclDashboardTab.showLoading()

        getItems()
    }

    override fun handleObservers() {
        viewModel.subSystemResponse.observe(viewLifecycleOwner, {
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                   setAdapter(list)


                } ?: kotlin.run {
                    rclDashboardTab.showNoData()
                }
            }
        })
    }

    private fun getItems() {
        val activity = activity ?: return

        val subSystemId = Const.SubSystems.KEY_REPORTS

        if(viewModel.isOfflineMode){
            viewModel.getSubSystemDb(subSystemId).observe(viewLifecycleOwner, {
                try {
                    txtLastUpdateDate.text = activity.lastUpdateDate(it.xUpdateDate)
                    convertJsonToModel(it.xJson)

                }catch (e: Exception){
                    rclDashboardTab.showNoData()
                    txtLastUpdateDate.text = getString(R.string.no_date)
                    activity.showOfflineMessage()
                }
            })
        }else{
            viewModel.getSubSystemList(
                subSystemId,
                BaseGetRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 4
                    request.jsonParameters = subSystemJson(subSystemId)
                }
            )
        }
    }

    private fun convertJsonToModel(json: String?) {
        viewModel.getSubSystemDb(json).observe(viewLifecycleOwner, { list ->
            setAdapter(list)
        })
    }

    private fun setAdapter(list: MutableList<SubSystemResponse.Result>) {

        if (list.size > 0)
            rclDashboardTab.view?.adapter = ItemsAdapter(list) { ssId, name ->

            }
        else
            rclDashboardTab.showNoData()
    }



    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclDashboardTab.showNoData()
                activity?.showError(it.message)
            }
        })
    }

    companion object{
        val TAG = "warehouse: ${ReportsDashboardFragment::class.java.simpleName}"
    }
}