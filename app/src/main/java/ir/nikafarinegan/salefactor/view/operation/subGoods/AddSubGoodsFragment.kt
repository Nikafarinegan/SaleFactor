package ir.nikafarinegan.salefactor.view.operation.subGoods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.successOperation
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.PostDocumentSubDetailRequest
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.view.scanner.BarcodeScannerListener
import kotlinx.android.synthetic.main.contain_add_sub_goods.*
import kotlinx.android.synthetic.main.fragment_add_sub_goods.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSubGoodsFragment(
    private val wddId: Long,
    private val goodsId: String,
    private val listener: OnActionListener
) : BaseFragment(), BarcodeScannerListener {

    private val viewModel by viewModel<SubGoodsViewModel>()
    private var barcodeKey: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_sub_goods, container, false)
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.responseId.observe(viewLifecycleOwner, {
            btnDone.loading(false)
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { result ->
                    if (result != 0L) {
                        activity.successOperation(it.message)
                        listener.onSave()

                    } else
                        activity.showError(it.message)
                } ?: kotlin.run {
                    activity.showError(it.message)
                }
            }
        })
    }

    override fun handleOnClickListeners() {

        (activity as AddSubGoodsActivity).setOnBarcodeListener(this)

        rdbIndividual.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                layoutGroup.visibility = View.GONE
                layoutIndividual.visibility = View.VISIBLE

            } else {
                layoutGroup.visibility = View.VISIBLE
                layoutIndividual.visibility = View.GONE
            }
        }

        btnDone.setOnClickListener {
            onSave()
        }
        btnBarcode.setOnClickListener {
            barcodeKey = KEY_BARCODE
            listener.onBarcode()
        }
        btnBarcodeFrom.setOnClickListener {
            barcodeKey = KEY_BARCODE_FROM
            listener.onBarcode()
        }
        btnBarcodeTo.setOnClickListener {
            barcodeKey = KEY_BARCODE_TO
            listener.onBarcode()
        }

    }

    override fun onScanResponse(result: String) {
        when (barcodeKey) {
            KEY_BARCODE -> edtSerialNum.setText(result)
            KEY_BARCODE_FROM -> edtSerialNumFrom.setText(result)
            KEY_BARCODE_TO -> edtSerialNumTo.setText(result)
        }
    }

    private fun onSave() {
        if (isValid) {
            btnDone.loading(true)
            viewModel.insertDocumentSubDetail(request)

        } else
            activity?.yToast(getString(R.string.fill_all_blanks), MessageStatus.ERROR)
    }

    private val isValid: Boolean
        get() {
            return (
                    if (rdbIndividual.isChecked)
                        edtSerialNum.text.toString().isNotEmpty()
                    else {
                        edtSerialNumFrom.text.toString()
                            .isNotEmpty() && edtSerialNumTo.text.toString().isNotEmpty()
                    })
        }


    override fun handleError() {
        super.handleError()
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnDone.loading(false)
                activity?.yToast(
                    it.message ?: getString(R.string.error_operation),
                    MessageStatus.ERROR
                )
            }
        })
    }

    private val request: PostDocumentSubDetailRequest
        get() {
            return PostDocumentSubDetailRequest().also { request ->
                request.wddId = wddId
                request.goodsId = goodsId
                request.userId = viewModel.userId
                request.financialYearId = viewModel.financialYearId
                request.registerDate = viewModel.registerDate

                if (rdbIndividual.isChecked) {
                    request.startSerialNum = edtSerialNum.text.toString()
                    request.endSerialNum = edtSerialNum.text.toString()
                } else {
                    request.startSerialNum = edtSerialNumFrom.text.toString()
                    request.endSerialNum = edtSerialNumTo.text.toString()
                }
                request.reusable = if (chbReusable.isChecked) 1 else 0
                request.registerDate = viewModel.registerDate
            }
        }

    interface OnActionListener {
        fun onBarcode()
        fun onSave()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${AddSubGoodsFragment::class.java.simpleName}"
        const val KEY_BARCODE = 1
        const val KEY_BARCODE_FROM = 2
        const val KEY_BARCODE_TO = 3
    }
}