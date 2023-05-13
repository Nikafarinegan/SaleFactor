package ir.nikafarinegan.salefactor.view.operation.document

import android.content.Intent
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentDetailListResponse
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentListResponse
import ir.nikafarinegan.salefactor.extentions.showBarcodeScanner
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.utils.Const.KEY_ITEM_NAME
import ir.nikafarinegan.salefactor.utils.Const.KEY_SSID
import ir.nikafarinegan.salefactor.view.base.BaseActivity
import ir.nikafarinegan.salefactor.view.noConnection.NoConnectionActivity
import ir.nikafarinegan.salefactor.view.operation.document.add_operation.AddDocumentFragment
import ir.nikafarinegan.salefactor.view.operation.document.detail.GoodsDetailFragment
import ir.nikafarinegan.salefactor.view.operation.document.document.DocumentFragment
import ir.nikafarinegan.salefactor.view.operation.document.goods.AddGoodsFragment
import ir.nikafarinegan.salefactor.view.operation.document.subDetail.SubDetailGoodsFragment
import ir.nikafarinegan.salefactor.view.scanner.BarcodeScannerListener

class DocumentActivity : BaseActivity() {

    private lateinit var operationType: OperationType
    private var subSystemSsId: Int = 0
    private var itemName: String? = null
    private var listener: BarcodeScannerListener? = null

    override fun setup() {

        subSystemSsId = intent.extras?.getInt(KEY_SSID) ?: 0
        operationType = getOperationType()
        itemName = intent.extras?.getString(KEY_ITEM_NAME) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_document)
        super.onCreate(savedInstanceState)

        gotoDocumentFragment()
    }

    private fun getOperationType(): OperationType {
        return when (subSystemSsId) {
            Const.OperationSubSystem.KEY_GOODS_RETURNED -> OperationType.GOODS_RETURNED
            Const.OperationSubSystem.KEY_WAREHOUSE_REFERRED -> OperationType.WAREHOUSE_REFERRED
            else -> OperationType.GOODS_RECEIPT
        }
    }

    private fun gotoDocumentFragment() {
        replaceFragmentInActivity(
            R.id.container,
            DocumentFragment(
                itemName ?: "",
                operationType,
                object : DocumentFragment.OnActionListener {
                    override fun onAdd(warehouseId: Long, formId: Long) {
                        gotoAddDocument(warehouseId, formId)
                    }

                    override fun onEdit(model: WarehouseDocumentListResponse.Result) {
                        gotoEditDocument(model)
                    }

                    override fun onItemClick(
                        model: WarehouseDocumentListResponse.Result,
                        warehouseId: Long
                    ) {
                        gotoGoodsDetailFragment(model, warehouseId)
                    }
                }),
            DocumentFragment.TAG
        )
    }

    private fun gotoEditDocument(result: WarehouseDocumentListResponse.Result) {
        replaceFragmentInActivity(
            R.id.container,
            AddDocumentFragment(
                itemName ?: "",
                result
            ),
            AddDocumentFragment.TAG
        )
    }

    private fun gotoAddDocument(warehouseId: Long, formId: Long) {
        replaceFragmentInActivity(
            R.id.container,
            AddDocumentFragment(
                itemName ?: "",
                warehouseId,
                formId
            ),
            AddDocumentFragment.TAG
        )
    }

    private fun gotoGoodsDetailFragment(
        documentListModel: WarehouseDocumentListResponse.Result,
        warehouseId: Long
    ) {
        replaceFragmentInActivity(
            R.id.container,
            GoodsDetailFragment(
                documentListModel,
                object : GoodsDetailFragment.OnActionListener {
                    override fun onAdd(wdId: Long) {
                        gotoAddGoodsFragment(documentListModel, warehouseId)
                    }

                    override fun onClick(model: WarehouseDocumentDetailListResponse.Result) {
                        gotoSubDetailGoods(model)
                    }

                    override fun onEdit(documentDetailModel: WarehouseDocumentDetailListResponse.Result) {
                        gotoEditGoodsFragment(warehouseId, documentDetailModel, documentListModel)
                    }
                }
            ),
            GoodsDetailFragment.TAG
        )
    }

    private fun gotoAddGoodsFragment(
        model: WarehouseDocumentListResponse.Result,
        warehouseId: Long,
    ) {
        replaceFragmentInActivity(
            R.id.container,
            AddGoodsFragment(
                warehouseId,
                object : AddGoodsFragment.OnActionListener {
                    override fun onDone() {
                        gotoGoodsDetailFragment(
                            model,
                            warehouseId
                        )
                    }

                    override fun onBarcode() {
                        showBarcodeScanner()
                    }
                }
            ),
            AddGoodsFragment.TAG
        )
    }

    private fun gotoEditGoodsFragment(
        warehouseId: Long,
        documentDetailModel: WarehouseDocumentDetailListResponse.Result,
        documentListModel: WarehouseDocumentListResponse.Result
    ) {
        replaceFragmentInActivity(
            R.id.container,
            AddGoodsFragment(
                warehouseId,
                documentDetailModel,
                object : AddGoodsFragment.OnActionListener {
                    override fun onDone() {
                        gotoGoodsDetailFragment(
                            documentListModel,
                            warehouseId
                        )
                    }

                    override fun onBarcode() {
                        showBarcodeScanner()
                    }
                }
            ),
            AddGoodsFragment.TAG
        )
    }

    private fun gotoSubDetailGoods(model: WarehouseDocumentDetailListResponse.Result) {
        replaceFragmentInActivity(
            R.id.container,
            SubDetailGoodsFragment(
                model
            ),
            SubDetailGoodsFragment.TAG
        )
    }

    fun setOnBarcodeListener(listener: BarcodeScannerListener) {
        this.listener = listener
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        try {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            result?.let {
                result.contents?.let {
                    listener?.onScanResponse(it)
                }

            } ?: kotlin.run {
                yToast(getString(R.string.response_error), MessageStatus.ERROR)
                super.onActivityResult(requestCode, resultCode, data)
            }
        } catch (e: Exception) {
            yToast(getString(R.string.response_error), MessageStatus.ERROR)
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showNoConnection() {
        startActivity(Intent(this, NoConnectionActivity::class.java))
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else
            this.finish()
    }
}
