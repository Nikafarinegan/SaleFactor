package ir.nikafarinegan.salefactor.view.operation.document.document

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.failedData
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.ChooseDialog
import ir.awlrhm.modules.view.RecyclerView
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.DeleteWarehouseDocumentRequest
import ir.nikafarinegan.salefactor.data.network.model.request.DocumentTypeListRequest
import ir.nikafarinegan.salefactor.data.network.model.request.WarehouseDocumentListRequest
import ir.nikafarinegan.salefactor.data.network.model.request.WarehouseListRequest
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentListResponse
import ir.nikafarinegan.salefactor.extentions.documentControlValidationJson
import ir.nikafarinegan.salefactor.extentions.documentTypeListJson
import ir.nikafarinegan.salefactor.extentions.warehouseDocumentListJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.operation.document.DocumentViewModel
import ir.nikafarinegan.salefactor.view.operation.document.OperationType
import kotlinx.android.synthetic.main.contain_document.*
import kotlinx.android.synthetic.main.fragment_document.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DocumentFragment(
    private val title: String,
    private val operationType: OperationType,
    private val listener: OnActionListener
) : BaseFragment() {

    private val viewModel by viewModel<DocumentViewModel>()
    private var listWarehouse: MutableList<ItemModel> = mutableListOf()
    private var listDocument: MutableList<ItemModel> = mutableListOf()
    private var warehouseId: Long = -1
    private var formId: Long = -1

    private var adapter: Adapter? = null
    private var pageNumber: Int = 1
    private var totalCount: Long = 0


    override fun setup() {
        txtTitle.text = title
        rclDocument
            .layoutManager(LinearLayoutManager(activity))
        spWarehouse.loading(true)
        spDocumentType.loading(true)
        initialList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_document, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!rclDocument.isOnLoading)
            rclDocument.showLoading()
        setDefaults()
    }

    private fun initialList() {
        val activity = activity ?: return

        adapter = Adapter(
            object : Adapter.OnActionListener {
                override fun onClick(result: WarehouseDocumentListResponse.Result) {
                    listener.onItemClick(result, warehouseId)
                    clear()
                }

                override fun onEdit(result: WarehouseDocumentListResponse.Result) {
                    listener.onEdit(result)
                    clear()
                }

                override fun onDelete(wdId: Long) {
                    ActionDialog.Builder()
                        .setTitle(getString(R.string.warning))
                        .setDescription(getString(R.string.are_you_sure_delete_item))
                        .setPositive(getString(R.string.yes)) {
                            actionLoading.isVisible = true
                            viewModel.deleteWarehouseDocument(
                                DeleteWarehouseDocumentRequest().also { request ->
                                    request.wdId = wdId
                                    request.userId = viewModel.userId
                                    request.financialYearId = viewModel.financialYearId
                                })
                        }
                        .setNegative(getString(R.string.no)) {}
                        .build()
                        .show(activity.supportFragmentManager, ActionDialog.TAG)
                }
            }
        )
        adapter?.let { adapter ->
            rclDocument.onActionRecyclerViewListener(object : RecyclerView.OnRecyclerViewListener {
                override fun onScrolled(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    dx: Int,
                    dy: Int
                ) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 1 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached && totalCount > adapter.itemCount) {
                        rclDocument.paging = true
                        pageNumber += 1
                        getWarehouseDocumentList()
                    }
                }
            })
        }
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        spWarehouse.setOnClickListener {
            spWarehouse.loading(true)
            showWarehouseList()
        }
        spDocumentType.setOnClickListener {
            spDocumentType.loading(true)
            showDocumentTypeList()
        }
        btnAdd.setOnClickListener {
            btnAdd.loading(true)
            documentControlValidation()
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
    }


    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.warehouseListResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spWarehouse.loading(false)
                it.result?.let { list ->
                    if (list.size > 0) {
                        listWarehouse.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    ItemModel(item.valueMember ?: 0, item.textMember ?: "")
                                )
                            }
                        }
                        spWarehouse.text = list[0].textMember ?: ""
                        warehouseId = list[0].valueMember ?: 0
                        getWarehouseDocumentList()

                    } else {
                        rclDocument.showNoData()
                        adapter?.clear()
                    }
                } ?: kotlin.run {
                    rclDocument.showNoData()
                    adapter?.clear()
                }
            }
        })

        viewModel.documentTypeResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spDocumentType.loading(false)
                it.result?.let { list ->
                    if (list.size > 0) {
                        listDocument.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    ItemModel(item.formId ?: 0, item.name ?: "")
                                )
                            }
                        }
                        spDocumentType.text = list[0].name ?: ""
                        formId = list[0].formId ?: 0
                        getWarehouseDocumentList()

                    } else {
                        spDocumentType.failedData()
                        rclDocument.showNoData()
                    }
                } ?: kotlin.run {
                    spDocumentType.failedData()
                    rclDocument.showNoData()
                }
            }
        })

        viewModel.warehouseDocumentListResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { result ->
                    if (result.size > 0) {
                        rclDocument.paging = false
                        totalCount = it.resultCount ?: 0
                        if (adapter?.itemCount == 0)
                            rclDocument?.view?.adapter = adapter
                        adapter?.setSource(result)
                    } else {
                        rclDocument.showNoData()
                        adapter?.clear()
                    }

                } ?: kotlin.run {
                    rclDocument.showNoData()
                    adapter?.clear()
                }
            }
        })


        viewModel.responseId.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                actionLoading.isVisible = false
                response.result?.let { result ->
                    if (result != 0L) {
                        activity.successOperation(response.message)
                        refresh()
                  } else
                    activity.showError(response.message)
            } ?: kotlin.run {
                activity.showError(response.message)
            }
        }
    })
        viewModel.documentControlValidationResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnAdd.loading(false)
                response.result?.let { list ->
                    val validation = list[0].messageValidation ?: ""
                    if (validation.isEmpty()) {
                        listener.onAdd(warehouseId, formId)
                        clear()
                    } else
                        activity.showError(response.message ?: getString(R.string.no_access))

                } ?: kotlin.run {
                    activity.showError(response.message ?: getString(R.string.no_access))
                }
            }
        })
    }

    private fun documentControlValidation(wdId: Long = 0) {
        viewModel.documentControlValidation(
            WarehouseDocumentListRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.wdId = wdId
                request.typeOperation = 5
                request.jsonParameters = documentControlValidationJson(
                    warehouseId,
                    formId,
                    Const.ControlValidation.Document.KEY_ADD
                )
            }
        )
    }

    private fun clear() {
        listWarehouse.clear()
        listDocument.clear()
        adapter?.clear()
        formId = -1
        warehouseId = -1
    }

    private fun refresh() {
        adapter?.clear()
        if (!rclDocument.isOnLoading)
            rclDocument.showLoading()
        getWarehouseDocumentList()
    }

    private fun setDefaults() {
        if (listWarehouse.isNotEmpty()) {
            spWarehouse.loading(false)
            spWarehouse.text = listWarehouse[0].title
            warehouseId = listWarehouse[0].id

        } else {
            spWarehouse.loading(false)
            getWarehouseList()
        }

        if (listDocument.isNotEmpty()) {
            spDocumentType.loading(false)
            spDocumentType.text = listDocument[0].title
            formId = listDocument[0].id

        } else {
            spDocumentType.loading(false)
            getDocumentTypeList()
        }
    }

    private fun getDocumentTypeList() {
        viewModel.getDocumentTypeList(
            DocumentTypeListRequest().also { request ->
                request.userId = viewModel.userId
                request.typeOperation = 20
                request.jsonParameters = documentTypeListJson(
                    when (operationType) {
                        OperationType.GOODS_RECEIPT -> 10
                        OperationType.GOODS_RETURNED -> 20
                        else -> 30
                    }
                )
            }
        )
    }

    private fun showWarehouseList() {
        val activity = activity ?: return
        spWarehouse.loading(false)
        if (listWarehouse.size > 0)
            ChooseDialog(
                listWarehouse,
                R.color.black,
                R.color.colorPrimary
            ) {
                warehouseId = it.id
                spWarehouse.text = it.title
                refresh()
            }
                .show(activity.supportFragmentManager, ChooseDialog.TAG)
        else
            getWarehouseList()
    }

    private fun getWarehouseList() {
        viewModel.getWarehouseList(
            WarehouseListRequest().also { request ->
                request.userId = viewModel.userId
                request.typeOperation = 11
            }
        )
    }

    private fun showDocumentTypeList() {
        val activity = activity ?: return
        spDocumentType.loading(false)
        if (listDocument.size > 0)
            ChooseDialog(
                listDocument,
                R.color.black,
                R.color.colorPrimary
            ) {
                formId = it.id
                spDocumentType.text = it.title
                refresh()

            }
                .show(activity.supportFragmentManager, ChooseDialog.TAG)
        else
            getDocumentTypeList()
    }


    private fun getWarehouseDocumentList() {
        if (warehouseId != -1L && formId != -1L)
            viewModel.getWarehouseDocumentList(
                WarehouseDocumentListRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYearId
                    request.pageNumber = pageNumber
                    request.typeOperation = 101
                    request.jsonParameters = warehouseDocumentListJson(warehouseId, formId, "")
                }
            )
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spWarehouse.loading(false)
                spDocumentType.loading(false)
                rclDocument.showNoData()
                activity?.yToast(
                    it.message ?: getString(R.string.response_error_all),
                    MessageStatus.ERROR
                )
            }
        })
        viewModel.errorDelete.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                actionLoading.isVisible = false
                activity?.showError(it.message)
            }
        })
    }

    interface OnActionListener {
        fun onAdd(
            warehouseId: Long,
            formId: Long
        )

        fun onEdit(model: WarehouseDocumentListResponse.Result)
        fun onItemClick(model: WarehouseDocumentListResponse.Result, warehouseId: Long)
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${DocumentFragment::class.java.simpleName}"
    }
}