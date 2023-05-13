package ir.nikafarinegan.salefactor.view.operation.goods
/*

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ir.awlrhm.modules.enums.MessageStatus
import ir.awrhm.modules.extensions.configProgressbar
import ir.awrhm.modules.extensions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.PostDocumentDetailRequest
import ir.nikafarinegan.salefactor.data.network.model.response.WHSGoodsListResponse
import ir.nikafarinegan.salefactor.view.base.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_add_goods.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddGoodsDialog(
    private val model: WHSGoodsListResponse.Result,
    private val _wdId: Long,
    private val callback: () -> Unit
) : BaseDialogFragment() {

    private val viewModel by viewModel<GoodsViewModel>()

    override fun setup() {
        activity?.configProgressbar(prcSave, R.color.save)
        txtTitle.text = getString(R.string.add_goods)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_goods, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        txtGoodsCode.text = model.goodsId.toString()
        txtUnit.text = model.unit
    }

    override fun handleOnClickListeners() {
        viewModel.response.observe(viewLifecycleOwner,{
            if (it.status == true) {
                activity?.yToast(
                    it.message ?: getString(R.string.success_operation),
                    MessageStatus.SUCCESS
                )
                callback.invoke()
            }
        })
    }

    override fun handleObservers() {
        layoutSave.setOnClickListener {

        }
        layoutCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun loading(visible: Boolean){
        if(visible){
            txtSave.visibility = View.GONE
            prcSave.visibility = View.VISIBLE
        }else{
            txtSave.visibility = View.VISIBLE
            prcSave.visibility = View.GONE
        }
    }



    override fun handleError() {
        viewModel.error.observe(viewLifecycleOwner,{
            loading(false)
            activity?.yToast(
                it.message ?: getString(R.string.response_error),
                MessageStatus.ERROR
            )
        })
    }

    companion object{
        val TAG = "warehouse: ${AddGoodsDialog::class.java.simpleName}"
    }
}*/
