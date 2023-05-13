package ir.nikafarinegan.salefactor.view.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.github.mikephil.charting.data.PieEntry
import ir.awlrhm.modules.extentions.showError
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.chart.Chart
import ir.nikafarinegan.salefactor.view.chart.ChartModel
import kotlinx.android.synthetic.main.contain_main_data.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    private val viewModel by viewModel<DashboardViewModel>()

    override fun setup() {
        txtUsername.text = viewModel.userFamily
        txtPostTitle.text = viewModel.userPosition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMainPageInformation()
    }

    private fun getMainPageInformation() {
        val activity = activity ?: return

        Chart(activity)
            .create(
                chart1,
                configPie(
                    mutableListOf<ChartModel>().apply {
                        this.add(ChartModel("تعداد رسیدها", 30f))
                        this.add(ChartModel("تعداد حواله ها", 40f))
                        this.add(ChartModel("تعداد تعداد برگشتی ها", 30f))
                    })
            )

        Chart(activity)
            .create(
                chart2,
                configPie(
                    mutableListOf<ChartModel>().apply {
                        this.add(ChartModel("انبار مرکزی", 30f))
                        this.add(ChartModel("انبار بهره برداری", 40f))
                        this.add(ChartModel("ساختمانی", 10f))
                        this.add(ChartModel("فوق توزیع", 20f))
                    })
            )
    }

    private fun configPie(list: MutableList<ChartModel>): ArrayList<PieEntry> {
        return arrayListOf<PieEntry>().also { pie ->
            list.forEach { value ->
                pie.add(PieEntry(value.value, value.title))
            }
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if(viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                activity?.showError(it.message)
            }
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${DashboardFragment::class.java.simpleName}"
    }
}