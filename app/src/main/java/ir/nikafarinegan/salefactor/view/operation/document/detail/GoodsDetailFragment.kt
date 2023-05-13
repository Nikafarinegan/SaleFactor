package ir.nikafarinegan.salefactor.view.operation.document.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.view.ActionDialog
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.DeleteWarehouseDocumentDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.request.WarehouseDocumentDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentDetailListResponse
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentListResponse
import ir.nikafarinegan.salefactor.extentions.documentDetailControlValidationJson
import ir.nikafarinegan.salefactor.extentions.warehouseDocumentDetailJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.operation.document.DocumentViewModel
import kotlinx.android.synthetic.main.fragment_goods_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GoodsDetailFragment(
    private val model: WarehouseDocumentListResponse.Result,
    private val listener: OnActionListener
) : BaseFragment() {

    private val viewModel by viewModel<DocumentViewModel>()
    private var pageNumber: Int = 1

    override fun setup() {
        txtTitle.text = getString(R.string.detail)
        rclGoodsDetail
            .layoutManager(LinearLayoutManager(activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_goods_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!rclGoodsDetail.isOnLoading)
            rclGoodsDetail.showLoading()
        getItems()
    }

    private fun getItems() {
        viewModel.getWarehouseDocumentDetailList(
            WarehouseDocumentDetailRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = warehouseDocumentDetailJson(model.wdId ?: 0)
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
        viewModel.documentDetailControlValidation(
            WarehouseDocumentDetailRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.typeOperation = 5
                request.jsonParameters = documentDetailControlValidationJson(
                    model.wdId ?: 0,
                    Const.ControlValidation.DocumentDetail.KEY_ADD
                )
            }
        )
    }


    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.warehouseDocumentDetailResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    if (list.size > 0) {
                        rclGoodsDetail.view?.adapter = Adapter(
                            list,
                            object : Adapter.OnActionListener {
                                override fun onClick(model: WarehouseDocumentDetailListResponse.Result) {
                                    listener.onClick(model)
                                }

                                override fun onEdit(model: WarehouseDocumentDetailListResponse.Result) {
                                    listener.onEdit(model)
                                }

                                override fun onDelete(wddId: Long) {
                                    ActionDialog.Builder()
                                        .setTitle(getString(R.string.warning))
                                        .setDescription(getString(R.string.are_you_sure_delete_item))
                                        .setPositive(getString(R.string.yes)) {
                                            rclGoodsDetail.actionLoading = true
                                            viewModel.deleteWarehouseDocumentDetail(
                                                DeleteWarehouseDocumentDetailRequest().also { request ->
                                                    request.wddId = wddId
                                                    request.userId = viewModel.userId
                                                    request.financialYearId =
                                                        viewModel.financialYearId
                                                })
                                        }
                                        .setNegative(getString(R.string.no)) {}
                                        .build()
                                        .show(activity.supportFragmentManager, ActionDialog.TAG)
                                }
                            })
                    } else
                        rclGoodsDetail.showNoData()

                } ?: kotlin.run {
                    rclGoodsDetail.showNoData()
                }
            }
        })

        viewModel.responseBoolean.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let { result ->
                    if (result) getItems()

                }
            }
        })
        viewModel.documentDetailControlValidationResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnAdd.loading(false)
                response.result?.let { list ->
                    val validation = list[0].messageValidation ?: ""
                    if (validation.isEmpty()) {
                        listener.onAdd(model.wdId ?: 0)

                    } else
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

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclGoodsDetail.showNoData()
                activity?.showError(it.message)
            }
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                rclGoodsDetail.actionLoading = false
                activity?.showError(it.message)
            }
        })
    }

    interface OnActionListener {
        fun onAdd(wdId: Long)
        fun onClick(model: WarehouseDocumentDetailListResponse.Result)
        fun onEdit(model: WarehouseDocumentDetailListResponse.Result)
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${GoodsDetailFragment::class.java.simpleName}"
    }
}