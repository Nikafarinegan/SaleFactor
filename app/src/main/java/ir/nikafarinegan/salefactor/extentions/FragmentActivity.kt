package ir.nikafarinegan.salefactor.extentions

import androidx.fragment.app.FragmentActivity
import com.google.zxing.integration.android.IntentIntegrator
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.checkCameraPhoneState
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.utils.OnPermissionListener
import ir.nikafarinegan.salefactor.view.scanner.ScannerActivity

fun FragmentActivity.showBarcodeScanner() {
    checkCameraPhoneState(object : OnPermissionListener {
        override fun onPermissionGranted() {
            initialBarcodeScanner()
        }

        override fun onPermissionDenied() {
            yToast(
                getString(ir.awrhm.modules.R.string.set_permission_for_operations),
                MessageStatus.ERROR
            )
        }
    })
}

private fun FragmentActivity.initialBarcodeScanner() {
    IntentIntegrator(this)
        .setCaptureActivity(ScannerActivity::class.java)
        .setOrientationLocked(false)
        .initiateScan()
}

