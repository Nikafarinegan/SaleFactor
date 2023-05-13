package ir.nikafarinegan.salefactor.view.operation.subGoods

import android.content.Intent
import android.os.Bundle
import com.google.zxing.integration.android.IntentIntegrator
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.extentions.*
import ir.nikafarinegan.salefactor.utils.Const.KEY_GOODS_ID
import ir.nikafarinegan.salefactor.utils.Const.KEY_WDD_ID
import ir.nikafarinegan.salefactor.view.base.BaseActivity
import ir.nikafarinegan.salefactor.view.noConnection.NoConnectionActivity
import ir.nikafarinegan.salefactor.view.scanner.BarcodeScannerListener
import kotlinx.android.synthetic.main.activity_add_sub_goods.*

class AddSubGoodsActivity : BaseActivity() {

    private var listener: BarcodeScannerListener? = null

    fun setOnBarcodeListener(listener: BarcodeScannerListener){
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub_goods)
        val wddId = intent.extras?.getLong(KEY_WDD_ID)
        val goodsId = intent.extras?.getString(KEY_GOODS_ID)


        replaceFragmentInActivity(
            R.id.container,
            AddSubGoodsFragment(
                wddId ?: 0,
                goodsId ?: "0",
                object : AddSubGoodsFragment.OnActionListener {
                    override fun onBarcode() {
                        showBarcodeScanner()
                    }

                    override fun onSave() {
                        this@AddSubGoodsActivity.finish()
                    }
                }),
            AddSubGoodsFragment.TAG
        )
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