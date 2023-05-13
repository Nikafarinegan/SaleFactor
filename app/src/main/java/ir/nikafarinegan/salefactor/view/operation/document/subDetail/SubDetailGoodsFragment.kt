package ir.nikafarinegan.salefactor.view.operation.document.subDetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.view.ActionDialog
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.DeleteDocumentSubDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.request.WarehouseDocumentSubDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentDetailListResponse
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentSubDetailResponse
import ir.nikafarinegan.salefactor.extentions.documentSubDetailControlValidationJson
import ir.nikafarinegan.salefactor.extentions.warehouseDocumentSubDetailJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.utils.Const.KEY_GOODS_ID
import ir.nikafarinegan.salefactor.utils.Const.KEY_WDD_ID
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.operation.document.DocumentActivity
import ir.nikafarinegan.salefactor.view.operation.document.DocumentViewModel
import ir.nikafarinegan.salefactor.view.operation.subGoods.AddSubGoodsActivity
import kotlinx.android.synthetic.main.fragment_sub_detail_goods.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubDetailGoodsFragment(
    val model: WarehouseDocumentDetailListResponse.Result
) : BaseFragment() {

    private val viewModel by viewModel<DocumentViewModel>()
    private var pageNumber: Int = 1


    override fun setup() {
        txtTitle.text = ""
        rclSubDetailGoods
            .layoutManager(LinearLayoutManager(activity))

        txtGoodsCode.text = model.goodId.toString()
        txtDescription.text = model.goodsName
        txtUnit.text = model.unit
        txtCount.text = model.amount.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sub_detail_goods, container, false)
    }

    private fun getItems() {
        viewModel.getWarehouseDocumentSubDetailList(
            WarehouseDocumentSubDetailRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = warehouseDocumentSubDetailJson(model.wddId ?: 0)
            }
        )
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        btnAdd.setOnClickListener {
            btnAdd.loading(true)
            controlValidation()
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
    }

    private fun controlValidation() {
        viewModel.subDetailControlValidation(
            WarehouseDocumentSubDetailRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.typeOperation = 5
                request.jsonParameters = documentSubDetailControlValidationJson(
                    model.wddId ?: 0,
                    Const.ControlValidation.SubDocumentDetail.KEY_ADD
                )
            }
        )
    }

    private fun gotoSubGoodsActivity() {
        val activity = activity ?: return
        val intent = Intent(activity, AddSubGoodsActivity::class.java)
        val bundle = Bundle()
        bundle.putString(KEY_GOODS_ID, model.goodId)
        bundle.putLong(KEY_WDD_ID, model.wddId ?: 0)
        intent.putExtras(bundle)
        activity.startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getItems()
    }

    override fun handleObservers() {
        val activity = activity as DocumentActivity

        viewModel.warehouseDocumentSubDetailResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    setAdapter(list)
                } ?: kotlin.run {
                    rclSubDetailGoods.showNoData()
                }
            }
        })

        viewModel.responseId.observe(viewLifecycleOwner, {
            rclSubDetailGoods.actionLoading = false
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { result ->
                    if (result != 0L) {
                        activity.successOperation(it.message)
                        rclSubDetailGoods.showLoading()
                        getItems()
                    } else
                        activity.showError(it.message)
                } ?: kotlin.run {
                    activity.showError(it.message)
                }
            }
        })

        viewModel.documentSubDetailControlValidationResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnAdd.loading(false)
                response.result?.let { list ->
                    val validation = list[0].messageValidation ?: ""
                    if (validation.isEmpty())
                        gotoSubGoodsActivity()
                    else
                        activity.showError(
                            response.message ?: getString(R.string.no_access),
                        )
                } ?: kotlin.run {
                    activity.showError(
                        response.message ?: getString(R.string.no_access),
                    )
                }
            }
        })
    }

    private fun setAdapter(
        list: MutableList<WarehouseDocumentSubDetailResponse.Result>
    ) {
        val activity = activity ?: return

        if (list.isEmpty())
            rclSubDetailGoods.showNoData()
        else {
            rclSubDetailGoods.view?.adapter = Adapter(
                list,
                object : Adapter.OnActionListener {
                    override fun onDelete(wdsdId: Long) {
                        ActionDialog.Builder()
                            .setTitle(getString(R.string.warning))
                            .setDescription(getString(R.string.are_you_sure_delete_item))
                            .setPositive(getString(R.string.yes)) {
                                rclSubDetailGoods.actionLoading = true
                                viewModel.deleteDocumentSubDetail(
                                    DeleteDocumentSubDetailRequest().also { request ->
                                        request.wdsdId = wdsdId
                                        request.userId = viewModel.userId
                                        request.financialYearId = viewModel.financialYearId
                                    }
                                )
                            }
                            .setNegative(getString(R.string.no)) {}
                            .build()
                            .show(activity.supportFragmentManager, ActionDialog.TAG)
                    }
                })
        }
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclSubDetailGoods.showNoData()
                activity?.yToast(
                    it.message ?: getString(R.string.response_error_all),
                    MessageStatus.ERROR
                )
            }
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclSubDetailGoods.actionLoading = false
                activity?.showError(it.message)
            }
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${SubDetailGoodsFragment::class.java.simpleName}"
    }
}