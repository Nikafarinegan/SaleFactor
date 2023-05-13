package ir.nikafarinegan.salefactor.view.scanner

interface BarcodeScannerListener {
    fun onScanResponse(result: String)
}