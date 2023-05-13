package ir.nikafarinegan.salefactor.view.scanner

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ViewfinderView
import ir.nikafarinegan.salefactor.R

class ScannerActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private var capture: CaptureManager? = null
    private var barcodeScannerView: DecoratedBarcodeView? = null
    private var viewfinderView: ViewfinderView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_scanner)
        super.onCreate(savedInstanceState)

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        viewfinderView = findViewById(R.id.zxing_viewfinder_view)
        (barcodeScannerView as DecoratedBarcodeView).setTorchListener(this)
        capture = CaptureManager(this, barcodeScannerView)
        capture?.initializeFromIntent(intent, savedInstanceState)
        capture?.setShowMissingCameraPermissionDialog(false)
        capture?.decode()
        /*val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        integrator.setPrompt(getString(R.string.scan_barcode))
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0) // Use a specific camera of the device
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()*/
    }

    override fun onTorchOn() {}

    override fun onTorchOff() {}

    override fun onResume() {
        super.onResume()
        capture?.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture?.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView?.onKeyDown(
            keyCode,
            event
        ) ?: kotlin.run { false } || super.onKeyDown(keyCode, event)
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

   /* override fun onActivityResult(
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
    }*/
}