package ir.nikafarinegan.salefactor.view.operation.document.add_operation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.models.DynamicModel
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.SearchableDialog
import ir.awlrhm.modules.view.searchablePagingDialog.SearchablePagingDialog
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.*
import ir.nikafarinegan.salefactor.data.network.model.response.*
import ir.nikafarinegan.salefactor.enums.DialogStatus
import ir.nikafarinegan.salefactor.extentions.*
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.operation.document.DocumentViewModel
import kotlinx.android.synthetic.main.contain_add_document.*
import kotlinx.android.synthetic.main.fragment_add_document.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddDocumentFragment() : BaseFragment() {

    constructor(
        title: String,
        warehouseId: Long,
        formId: Long,
    ) : this() {
        this.title = title
        this.warehouseId = warehouseId
        this.formId = formId
    }

    constructor(
        title: String,
        model: WarehouseDocumentListResponse.Result,
    ) : this() {
        this.title = title
        documentModel = model
        warehouseId = model.warehouseId
        formId = model.formId
        isOnEditMode = true
    }

    private val viewModel by viewModel<DocumentViewModel>()

    private var isOnEditMode: Boolean = false
    private var title: String? = null
    private var warehouseId: Long? = -1
    private var formId: Long? = -1
    private var documentModel: WarehouseDocumentListResponse.Result? = null

    private var listReserveWarehouseDocument: List<WarehouseDocumentListResponse.Result> =
        emptyList()

    private var listPlace: MutableList<ItemModel> = mutableListOf()
    private var listContract: MutableList<ItemModel> = mutableListOf()
    private var listWarehouse: MutableList<ItemModel> = mutableListOf()
    private var listFocus: MutableList<ItemModel> = mutableListOf()

    private lateinit var workOrderDialog: SearchablePagingDialog<WorkOrderResponse.Result>
    private var workOrderDialogStatus = DialogStatus.CLICKED

    private lateinit var relatedDocumentDialog: SearchablePagingDialog<WarehouseDocumentListResponse.Result>
    private var relatedDocumentDialogStatus = DialogStatus.CLICKED

    private lateinit var projectDialog: SearchablePagingDialog<ProjectListResponse.Result>
    private var projectDialogStatus = DialogStatus.CLICKED

    private lateinit var accountingCodeDialog: SearchablePagingDialog<AccountingCodeResponse.Result>
    private var accountingCodeDialogStatus = DialogStatus.CLICKED

    private lateinit var customerDialog: SearchablePagingDialog<CustomerSearchListResponse.Result>
    private var customerDialogStatus = DialogStatus.CLICKED

    private var placeId: Long? = 0
    private var woId: Long = 0
    private var customerId: Long = 0
    private var projectId: Long? = 0
    private var contractId: Long? = 0
    private var wdId: Long? = 0
    private var reserveNo: Long = 0
    private var wdParentId: Long = 0
    private var wdCode: String? = "0"
    private var acId: Long? = 0
    private var focusId: Long = 0
    private var reserveWarehouseId: Long = 0
    private var reserveFinancialYearId: Int = 0

    @SuppressLint("SetTextI18n")
    override fun setup() {
        dvDocument.date = viewModel.currentDate

        //edit mode
        documentModel?.let { model ->
            txtTitle.text = "${getString(R.string.edit)} $title"
            isOnEditMode = true
            wdId = model.wdId ?: 0
            wdParentId = model.wdParentId ?: 0
            reserveNo = model.reserveNo ?: 0
            edtDocumentNo.setText(model.wdCode)
            placeId = model.placeId ?: 0
            spPlace.text = model.placeNote ?: ""
            customerId = model.customerId ?: 0
            spCustomer.text = model.customerTitle ?: ""
            woId = model.woId ?: 0
            spWorkOrderNo.text = model.woTitle ?: ""
            projectId = model.projectId ?: 0
            spProject.text = model.projectName ?: ""
            contractId = model.contractId ?: 0
            spContract.text = model.contractTitle ?: ""
            dvDocument.date = model.wdDate ?: viewModel.currentDate
            spAccountingCode.text = model.acNameTemp ?: ""
            acId = model.acId ?: 0
            spRelatedDocument.text = model.reserveName ?: ""
            spRelatedWarehouse.text = model.reserveWarehouseName ?: ""
            reserveWarehouseId = model.reserveWarehouseId ?: 0
            spFocus.text = model.caSubject ?: ""
            focusId = model.caId ?: 0
            edtWeighBridgeNo.setText(model.weighBridgeDescription)
            edtDescription.setText(model.note)
            wdCode = model.wdCode ?: "0"
            reserveFinancialYearId = model.reserveFinancialYearId ?: 0
        } ?: kotlin.run {
            txtTitle.text = "${getString(R.string.add)} $title"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_document, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isOnEditMode)
            viewModel.getDocumentNumber(
                DocumentNumberRequest().also { request ->
                    request.userId = viewModel.userId
                    request.financialYearId = viewModel.financialYearId
                    request.typeOperation = 501
                    request.jsonParameters = getDocumentNumberJson(
                        formId ?: 0,
                        warehouseId ?: 0
                    )
                }
            )

        initDialogs()
    }

    private fun initDialogs() {
        workOrderDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<WorkOrderResponse.Result> {
                override fun onChoose(model: DynamicModel<WorkOrderResponse.Result>) {
                    spWorkOrderNo.text = model.title
                    woId = model.dynamic.woId ?: 0
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getWorkOrderList(pageNumber, search)
                }

                override fun onDismiss() {
                    workOrderDialogStatus = DialogStatus.DISMISSED
                }
            }
        )
        relatedDocumentDialog = SearchablePagingDialog(
            object :
                SearchablePagingDialog.OnActionListener<WarehouseDocumentListResponse.Result> {
                override fun onChoose(model: DynamicModel<WarehouseDocumentListResponse.Result>) {
                    spRelatedDocument.text = model.title
                    wdParentId = model.dynamic.wdId ?: 0
                    listReserveWarehouseDocument.forEach { item ->
                        if (item.wdId == model.dynamic.wdId) {
                            setDefaultValues(item)
                        }
                    }
                }

                override fun onDismiss() {
                    relatedDocumentDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getReserveWarehouseDocument(
                        pageNumber,
                        search
                    )
                }
            }
        )
        projectDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<ProjectListResponse.Result> {
                override fun onChoose(model: DynamicModel<ProjectListResponse.Result>) {
                    spProject.text = model.title
                    projectId = model.dynamic.projectId
                }

                override fun onDismiss() {
                    projectDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getProjectList(pageNumber, search)
                }
            }
        )
        accountingCodeDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<AccountingCodeResponse.Result> {
                override fun onChoose(model: DynamicModel<AccountingCodeResponse.Result>) {
                    acId = model.dynamic.acId
                    spAccountingCode.text = model.title
                }

                override fun onDismiss() {
                    accountingCodeDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getAccountingCode(pageNumber, search)
                }
            }
        )
        customerDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<CustomerSearchListResponse.Result> {
                override fun onChoose(model: DynamicModel<CustomerSearchListResponse.Result>) {
                    customerId = model.dynamic.customerId ?: 0
                    spCustomer.text = model.title
                }

                override fun onDismiss() {
                    customerDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    getCustomerSearchList(pageNumber, search)
                }
            }
        )
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.workOrderResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spWorkOrderNo.loading(false)
                if (workOrderDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    if (list.isNotEmpty()) {
                        val listWorkOrder = mutableListOf<DynamicModel<WorkOrderResponse.Result>>()
                        listWorkOrder.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    DynamicModel(item.woTitle ?: "", item)
                                )
                            }
                        }
                        workOrderDialog.setSource(response.resultCount ?: 0, listWorkOrder)
                        if (!workOrderDialog.isVisible)
                            workOrderDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )

                    } else if (workOrderDialog.isVisible)
                        workOrderDialog.showNoData()
                }
            }
        })
        viewModel.projectResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spProject.loading(false)
                if (projectDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    if (list.isNotEmpty()) {
                        val listProject = mutableListOf<DynamicModel<ProjectListResponse.Result>>()
                        listProject.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    DynamicModel(item.projectBudgetTitle ?: "", item)
                                )
                            }
                        }
                        projectDialog.setSource(response.resultCount ?: 0, listProject)
                        if (!projectDialog.isVisible)
                            projectDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )
                    } else {
                        if (projectDialog.isVisible)
                            projectDialog.showNoData()
                        else
                            spProject.visibility = View.GONE
                    }
                }
            }
        })
        viewModel.accountingCodeResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spAccountingCode.loading(false)
                if (accountingCodeDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    if (list.isNotEmpty()) {
                        val listAccountingCode =
                            mutableListOf<DynamicModel<AccountingCodeResponse.Result>>()
                        listAccountingCode.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    DynamicModel(item.acName ?: "", item)
                                )
                            }
                        }
                        accountingCodeDialog.setSource(
                            response.resultCount ?: 0,
                            listAccountingCode
                        )
                        if (!accountingCodeDialog.isVisible)
                            accountingCodeDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )

                    } else if (accountingCodeDialog.isVisible)
                        accountingCodeDialog.showNoData()
                }
            }
        })
        viewModel.customerResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spCustomer.loading(false)
                if (customerDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    if (list.isNotEmpty()) {
                        val listCustomer = mutableListOf<DynamicModel<CustomerSearchListResponse.Result>>()
                        listCustomer.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    DynamicModel(item.customerTitle ?: "", item)
                                )
                            }
                        }
                        customerDialog.setSource(response.resultCount ?: 0, listCustomer)
                        if (!customerDialog.isVisible)
                            customerDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )

                    } else if (customerDialog.isVisible)
                        customerDialog.showNoData()
                }
            }
        })
        viewModel.reserveWarehouseDocumentResponse.observe(
            viewLifecycleOwner,
            { response ->
                spRelatedDocument.loading(false)
                if (relatedDocumentDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    listReserveWarehouseDocument = list
                    if (list.isNotEmpty()) {
                        val listRelatedDocument =
                            mutableListOf<DynamicModel<WarehouseDocumentListResponse.Result>>()
                        listRelatedDocument.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    DynamicModel(item.wdSubject ?: "", item)
                                )
                            }
                        }
                        relatedDocumentDialog.setSource(
                            response.resultCount ?: 0,
                            listRelatedDocument
                        )
                        if (!relatedDocumentDialog.isVisible)
                            relatedDocumentDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )

                    } else if (relatedDocumentDialog.isVisible)
                        relatedDocumentDialog.showNoData()
                }
            })
        viewModel.placeListResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spPlace.loading(false)
                it.result?.let { list ->
                    if (list.size > 0) {
                        listPlace.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    ItemModel(item.placeId ?: 0, item.placeName ?: "")
                                )
                            }
                        }
                        showPlaces()
                    }
                }
            }
        })
        viewModel.contractResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spContract.loading(false)
                it.result?.let { list ->
                    if (list.isNotEmpty()) {
                        if (spContract.visibility != View.VISIBLE)
                            spContract.visibility = View.VISIBLE

                        listContract.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    ItemModel(item.contractId ?: 0, item.contractTitle ?: "")
                                )
                            }
                        }
                    } else
                        spContract.visibility = View.GONE
                } ?: kotlin.run {
                    spContract.visibility = View.GONE
                }
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it.result != 0L) {
                    activity.yToast(
                        it.message ?: getString(R.string.success_operation),
                        MessageStatus.SUCCESS
                    )
                    activity.onBackPressed()
                } else
                    activity.yToast(
                        it.message ?: getString(R.string.failed_operation),
                        MessageStatus.ERROR
                    )
            }
        })
        viewModel.customerAccountByCustomerIdResponse.observe(
            viewLifecycleOwner,
            { response ->
                spFocus.loading(false)
                response.result?.let { list ->
                    if (list.isNotEmpty()) {
                        if (spFocus.visibility != View.VISIBLE)
                            spFocus.visibility = View.VISIBLE

                        listFocus.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    ItemModel(item.caId ?: 0, item.caSubject ?: "")
                                )
                            }
                        }
                    } else
                        spFocus.visibility = View.GONE
                } ?: kotlin.run {
                    spFocus.visibility = View.GONE
                }
            })
        viewModel.warehouseListResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                spRelatedWarehouse.loading(false)
                it.result?.let { list ->
                    if (list.size > 0) {
                        listWarehouse.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    ItemModel(item.valueMember ?: 0, item.textMember ?: "")
                                )
                            }
                        }
                        showWarehouses()
                    }
                }
            }
        })
        viewModel.documentCodeResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    if(list.isNotEmpty()) {
                     val documentCode = list[0].maxCode ?: 0
                        edtDocumentNo.setText(documentCode.toString())
                        wdCode = documentCode.toString()
                    }else{
                        activity.showError(it.message ?: getString(R.string.document_number_is_empty))
                    }
                }?: kotlin.run {
                    activity.showError(it.message ?: getString(R.string.document_number_is_empty))
                }
            }
        })
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        spRelatedDocument.setOnClickListener {
            if (!spRelatedDocument.isLoading) {
                spRelatedDocument.loading(true)
                relatedDocumentDialogStatus = DialogStatus.CLICKED
                getReserveWarehouseDocument(1)
            }
        }
        spPlace.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spPlace.isLoading) {
                    spPlace.loading(true)
                    showPlaces()
                }
            }
        }
        spWorkOrderNo.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spWorkOrderNo.isLoading) {
                    workOrderDialogStatus = DialogStatus.CLICKED
                    spWorkOrderNo.loading(true)
                    getWorkOrderList(1)
                }
            }
        }
        spCustomer.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spCustomer.isLoading) {
                    spCustomer.loading(true)
                    customerDialogStatus = DialogStatus.CLICKED
                    getCustomerSearchList(1)
                }
            }
        }
        spProject.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spProject.isLoading)
                    if (woId > 0L) {
                        spProject.loading(true)
                        projectDialogStatus = DialogStatus.CLICKED
                        getProjectList(1)
                    } else
                        activity.yToast(
                            getString(R.string.choose_work_order_at_first),
                            MessageStatus.ERROR
                        )
            }
        }
        spAccountingCode.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spAccountingCode.isLoading) {
                    spAccountingCode.loading(true)
                    accountingCodeDialogStatus = DialogStatus.CLICKED
                    getAccountingCode(1)
                }
            }
        }
        spContract.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spContract.isLoading)
                    if (customerId > 0L) {
                        spContract.loading(true)
                        showContracts()
                    } else
                        activity.yToast(
                            getString(R.string.choose_customer_at_first),
                            MessageStatus.ERROR
                        )
            }
        }
        spRelatedWarehouse.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spRelatedWarehouse.isLoading) {
                    spRelatedWarehouse.loading(true)
                    showWarehouses()
                }
            }
        }

        spFocus.setOnClickListener {
            if (wdParentId <= 0L && !isOnEditMode)
                showChooseFirstMessage()
            else {
                if (!spFocus.isLoading) {
                    if (customerId > 0L) {
                        spFocus.loading(true)
                        showFocus()
                    } else
                        activity.yToast(
                            getString(R.string.choose_customer_at_first),
                            MessageStatus.ERROR
                        )
                }
            }
        }

        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        btnDone.setOnClickListener {
            if (isValid) {
                btnDone.loading(true)
                if (isOnEditMode)
                    viewModel.updateWarehouseDocument(request)
                else
                    viewModel.insertWarehouseDocument(request)

            } else
                activity.yToast(getString(R.string.fill_all_blanks), MessageStatus.ERROR)
        }
    }

    private fun getAccountingCode(
        pageNumber: Int,
        search: String = ""
    ) {
        viewModel.getAccountingCode(
            AccountingCodeReqeust().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = searchJson(search)
            }
        )
    }

    private fun getReserveWarehouseDocument(
        pageNumber: Int,
        search: String = ""
    ) {
        viewModel.getReserveWarehouseDocument(
            WarehouseDocumentListRequest().also { request ->
                request.financialYearId = viewModel.financialYearId
                request.userId = viewModel.userId
                request.typeOperation = 102
                request.pageNumber = pageNumber
                request.jsonParameters = reserveWarehouseDocumentJson(
                    warehouseId ?: 0,
                    formId ?: 0,
                    search
                )
            }
        )
    }

    private fun getCustomerSearchList(
        pageNumber: Int,
        search: String = ""
    ) {
        viewModel.getCustomerSearchList(
            CustomerSearchListRequest().also { request ->
                request.userId = viewModel.userId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = searchJson(search)
            }
        )
    }

    private fun getProjectList(
        pageNumber: Int,
        search: String = ""
    ) {
        woId
        viewModel.getProjectList(
            ProjectListRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = projectListJson(woId, search)
            }
        )
    }

    private fun getWorkOrderList(pageNumber: Int, search: String = "") {
        viewModel.getWorkOrderList(
            WorkOrderRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.pageNumber = pageNumber
                request.typeOperation = 101
                request.jsonParameters = searchJson(search)
            }
        )
    }

    private fun showChooseFirstMessage() {
        activity?.yToast(
            getString(R.string.choose_related_document_first),
            MessageStatus.ERROR
        )
    }

    private fun showFocus() {
        val activity = activity ?: return

        if (listFocus.isNotEmpty()) {
            spFocus.loading(false)
            SearchableDialog(listFocus) {
                focusId = it.id
                spFocus.text = it.title
            }
                .show(activity.supportFragmentManager, SearchableDialog.TAG)
        } else
            getFocus(1)
    }

/*    private fun showAccountingCodes() {
        val activity = activity ?: return

        if (listAccountingCode.isNotEmpty()) {
            spAccountingCode.loading(false)
            SearchableDialog(listAccountingCode) {
                _acId = it.id
                spAccountingCode.text = it.title
            }
                .show(activity.supportFragmentManager, SearchableDialog.TAG)
        } else
            viewModel.getAccountingCode()
    }*/

    private fun showWarehouses() {
        val activity = activity ?: return

        if (listWarehouse.isNotEmpty()) {
            spRelatedWarehouse.loading(false)

            SearchableDialog(listWarehouse) {
                reserveWarehouseId = it.id
                spRelatedWarehouse.text = it.title
            }
                .show(activity.supportFragmentManager, SearchableDialog.TAG)
        } else
            viewModel.getWarehouseList(
                WarehouseListRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 11
                }
            )
    }

/*    private fun showRelatedDocument() {
        val activity = activity ?: return

        if (listRelatedDocument.isNotEmpty()) {
            spRelatedDocument.loading(false)
            SearchableDialog(listRelatedDocument) { item ->
                spRelatedDocument.text = item.title
                _wdParentId = item.id
                listReserveWarehouseDocument.forEach { model ->
                    if (model.wdId == item.id) {
                        activity.setDefaultValues(model)
                    }
                }
            }
                .show(activity.supportFragmentManager, SearchableDialog.TAG)
        } else
            viewModel.getReserveWarehouseDocument(
                _warehouseId ?: 0,
                _formId ?: 0,
                viewModel.financialYear
            )
    }*/

    private fun setDefaultValues(model: WarehouseDocumentListResponse.Result) {
        spRelatedWarehouse.text = model.warehouseName
        reserveWarehouseId = model.warehouseId ?: 0

        spPlace.text = model.placeNote
        placeId = model.placeId ?: 0

        spWorkOrderNo.text = model.woTitle
        woId = model.woId ?: 0

        spCustomer.text = model.customerTitle
        customerId = model.customerId ?: 0

        if (model.caId != 0L) {
            spFocus.text = model.caSubject
            focusId = model.caId ?: 0
        } else
            spFocus.visibility = View.GONE

        if (model.contractId != 0L) {
            spContract.text = model.contractTitle
            contractId = model.contractId ?: 0
        } else
            spContract.visibility = View.GONE

        spProject.text = model.projectName
        projectId = model.projectId ?: 0

        spAccountingCode.text = model.acNameTemp
        acId = model.acId ?: 0

        reserveFinancialYearId = model.reserveFinancialYearId ?: 0

        edtWeighBridgeNo.setText(model.weighBridgeDescription)

        edtDescription.setText(model.note)

        reserveNo = model.wdCode?.toLong() ?: 0L
        wdParentId = model.wdId ?: 0
    }

    private fun showContracts() {
        val activity = activity ?: return

        if (listContract.isNotEmpty()) {
            spContract.loading(false)
            SearchableDialog(listContract) {
                contractId = it.id
                spContract.text = it.title
            }
                .show(activity.supportFragmentManager, SearchableDialog.TAG)
        } else
            getContract()
    }

    private fun getContract(search: String = "") {
        viewModel.getContract(
            ContractRequest().also { request ->
                request.userId = viewModel.userId
                request.typeOperation = 12
                request.jsonParameters = contractJson(customerId, search)
            }
        )
    }

    private fun getFocus(
        pageNumber: Int,
        search: String = ""
    ) {
        viewModel.getCustomerAccountByCustomerId(
            CustomerAccountRequest().also { request ->
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.typeOperation = 101
                request.pageNumber = pageNumber
                request.jsonParameters = customerAccountJson(customerId, search)
            }
        )
    }

    private fun showPlaces() {
        val activity = activity ?: return
        if (listPlace.isNotEmpty()) {
            spPlace.loading(false)
            SearchableDialog(listPlace) {
                spPlace.text = it.title
                placeId = it.id
            }
                .show(activity.supportFragmentManager, SearchableDialog.TAG)
        } else
            viewModel.getPlaceList(
                PlaceRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 15
                }
            )
    }

    private val request: PostWarehouseDocumentRequest
        get() {
            return PostWarehouseDocumentRequest().also { request ->
                request.wdId = wdId
                request.wdCode = wdCode
                request.placeId = placeId
                request.wdParentId = wdParentId
                request.formId = formId
                request.customerId = customerId
                request.contractId = contractId
                request.woId = woId
                request.projectId = projectId
                request.wdDate = dvDocument.date
                request.warehouseId = warehouseId
                request.reserveNo = reserveNo
                request.reserveWarehouseId = reserveWarehouseId
                request.wdSubject = spRelatedDocument.text.toString()
                request.reserveFinancialYearId = reserveFinancialYearId
                request.caId = focusId
                request.basculDescription = edtWeighBridgeNo.text.toString()
                request.wdNote = edtDescription.text.toString()
                request.acId = acId
                request.registerDate = viewModel.registerDate
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.registerDate = viewModel.registerDate
            }
        }
    private val isValid: Boolean
        get() {
            return placeId != -1L &&
                    woId != -1L &&
                    customerId != -1L &&
                    projectId != -1L
        }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnDone.loading(false)
                activity?.showError(it.message)
            }
        })
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${AddDocumentFragment::class.java}"
    }
}