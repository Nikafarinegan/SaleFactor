package ir.nikafarinegan.salefactor.view.operation.document.goods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.models.DynamicModel
import ir.awlrhm.modules.view.searchablePagingDialog.SearchablePagingDialog
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.PostDocumentDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.request.SearchGoodsListRequest
import ir.nikafarinegan.salefactor.data.network.model.response.WHSGoodsListResponse
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentDetailListResponse
import ir.nikafarinegan.salefactor.enums.DialogStatus
import ir.nikafarinegan.salefactor.extentions.searchGoodsListJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.operation.document.DocumentActivity
import ir.nikafarinegan.salefactor.view.operation.document.DocumentViewModel
import ir.nikafarinegan.salefactor.view.scanner.BarcodeScannerListener
import kotlinx.android.synthetic.main.contain_add_goods.*
import kotlinx.android.synthetic.main.fragment_add_goods.*
import kotlinx.android.synthetic.main.fragment_add_goods.btnBack
import kotlinx.android.synthetic.main.fragment_add_goods.btnDone
import kotlinx.android.synthetic.main.fragment_add_goods.txtTitle
import kotlinx.android.synthetic.main.fragment_add_sub_goods.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddGoodsFragment(
    private val warehouseId: Long,
    private val listener: OnActionListener
) : BaseFragment(), BarcodeScannerListener {

    constructor(
        warehouseId: Long,
        model: WarehouseDocumentDetailListResponse.Result,
        listener: OnActionListener
    ): this(
        warehouseId,
        listener
    ){
        this.model = model
    }

    private val viewModel by viewModel<DocumentViewModel>()
    private var model: WarehouseDocumentDetailListResponse.Result?= null
    private lateinit var goodsDialog: SearchablePagingDialog<WHSGoodsListResponse.Result>
    private var goodsDialogStatus = DialogStatus.CLICKED
    private var goodsId: String = "0"

    private var isOnEditMode: Boolean = false

    override fun setup() {
        model?.let {model ->
            isOnEditMode = true
            txtTitle.text = getString(R.string.edit_goods)
            edtCount.setText(model.amount.toString())
            edtSerialNum.setText(model.serialNo)
            edtGoodsCode.setText(model.goodId)
            edtGoodsName.setText(model.goodsName)
            edtUnit.setText(model.unit)

        }?: kotlin.run {
            txtTitle.text = getString(R.string.add_goods)
        }

        goodsDialog = SearchablePagingDialog(
            object : SearchablePagingDialog.OnActionListener<WHSGoodsListResponse.Result> {
                override fun onChoose(model: DynamicModel<WHSGoodsListResponse.Result>) {
                    goodsId = model.dynamic.goodsId ?: "0"
                    edtGoodsCode.setText(goodsId)
                    edtGoodsName.setText(model.dynamic.goodsName)
                    edtUnit.setText(model.dynamic.unit)
                }

                override fun onDismiss() {
                    goodsDialogStatus = DialogStatus.DISMISSED
                }

                override fun onSearchPaging(pageNumber: Int, search: String) {
                    viewModel.getSearchGoodsList(
                        SearchGoodsListRequest().also { request ->
                            request.userId = viewModel.userId
                            request.financialYearId = viewModel.financialYearId
                            request.pageNumber = pageNumber
                            request.typeOperation = 102
                            request.jsonParameters =
                                searchGoodsListJson(warehouseId, search, viewModel.startDate)
                        })
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_goods, container, false)
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        (activity as DocumentActivity).setOnBarcodeListener(this)

        btnSearch.setOnClickListener {
            search()
        }
        tilGoodsCode.setOnClickListener {
            search()
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
        btnDone.setOnClickListener {
            if (isValid) {
                btnDone.loading(true)
                if(isOnEditMode)
                    viewModel.updateDocumentDetail(request)
                else
                    viewModel.insertDocumentDetail(request)

            } else
                activity.yToast(getString(R.string.fill_all_blanks), MessageStatus.ERROR)
        }
        btnBarcode.setOnClickListener {
            listener.onBarcode()
        }
    }

    override fun onScanResponse(result: String) {
        edtSerialNum.setText(result)
    }

    private fun search() {
        val activity = activity ?: return
        goodsDialogStatus = DialogStatus.CLICKED
        goodsDialog.show(
            activity.supportFragmentManager,
            SearchablePagingDialog.TAG
        )
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.searchGoodsResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnDone.loading(false)
                if (goodsDialogStatus != DialogStatus.CLICKED)
                    return@observe

                response.result?.let { list ->
                    if (list.isNotEmpty()) {
                        val listGoods = mutableListOf<DynamicModel<WHSGoodsListResponse.Result>>()
                        listGoods.also { listModel ->
                            list.forEach { item ->
                                listModel.add(
                                    DynamicModel(item.goodsName ?: "", item)
                                )
                            }
                        }
                        goodsDialog.setSource(response.resultCount ?: 0, listGoods)
                        if (!goodsDialog.isVisible)
                            goodsDialog.show(
                                activity.supportFragmentManager,
                                SearchablePagingDialog.TAG
                            )

                    } else if (goodsDialog.isVisible)
                        goodsDialog.showNoData()
                }?: kotlin.run {
                    goodsDialog.showNoData()
                }
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner, {
            btnDone.loading(false)
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { result ->
                    if (result != 0L) {
                        activity.successOperation(it.message)
                        listener.onDone()

                    } else
                        activity.showError(it.message)
                } ?: kotlin.run {
                    activity.showError(it.message)
                }
            }
        })
    }

    private val request: PostDocumentDetailRequest
    get(){
        return PostDocumentDetailRequest().also { request ->
            request.financialYearId = viewModel.financialYearId
            request.userId = viewModel.userId
            request.amount = edtCount.text.toString().toInt()
            request.serialNo = edtSerialNum.text.toString()
            request.goodsId = goodsId
            request.wdId = model?.wddId ?: 0
            request.registerDate = viewModel.registerDate
        }
    }

    private val isValid: Boolean
        get() {
            return edtSerialNum.text.toString().isNotEmpty() &&
                    edtCount.text.toString().isNotEmpty()
        }

    override fun handleError() {
        super.handleError()
        val activity = activity ?: return

        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnDone.loading(false)
               activity.showError(it.message)
            }
        })
    }

    interface OnActionListener {
        fun onDone()
        fun onBarcode()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${AddGoodsFragment::class.java.simpleName}"
    }
}