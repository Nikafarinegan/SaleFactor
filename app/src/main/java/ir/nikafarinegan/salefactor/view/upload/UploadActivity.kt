package ir.nikafarinegan.salefactor.view.upload

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.*
import ir.nikafarinegan.salefactor.R
import kotlinx.android.synthetic.main.activity_upload.*
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.system.exitProcess


class UploadActivity : AppCompatActivity() {

    companion object {
        val PATH =
            Environment.getExternalStorageDirectory().toString() + File.separator + "SaleFactor"
        val excelFile =
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "SaleFactor" + File.separator + "test.xlsx"
        const val CERTIFICATE_NAME = "SaleCertificate"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        handleEvents()
    }

    private fun handleEvents() {
        buttonUploadCertificate.setOnClickListener {
            checkReadWriteStoragePermission {
                pickCertificate()
            }

        }
        buttonUploadExcel.setOnClickListener {
            checkReadWriteStoragePermission {
                pickExcelFile()
//                writeExcel()
//                readExcel()
            }
        }
    }

    private fun pickExcelFile() {
        val intent = Intent(this, FilePickerActivity::class.java)
        configExcelFileChooser(intent)
        chooseExcelResultLauncher.launch(intent)
    }

    private fun pickCertificate() {
        val intent = Intent(this, FilePickerActivity::class.java)
        configCertificateFileChooser(intent)
        certificateResultLauncher.launch(intent)
    }

    private fun configCertificateFileChooser(intent: Intent) {
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowFiles(true)
                .setShowImages(true)
                .setSuffixes("cer")
                .setSingleChoiceMode(true)
                .setSingleClickSelection(true)
                .setSkipZeroSizeFiles(true)
                .build()
        )
    }


    private fun configExcelFileChooser(intent: Intent) {
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowFiles(true)
                .setShowImages(false)
                .setSuffixes("xls", "xlsx")
                .setSingleChoiceMode(true)
                .setSingleClickSelection(true)
                .setSkipZeroSizeFiles(true)
                .build()
        )
    }

    private val certificateResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                val files: ArrayList<MediaFile>? =
                    result.data?.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
                files?.let {
                    saveCertificateFile(files[0])
                } ?: showError(getString(R.string.error_read_certificate))
            }
        }

    private val chooseExcelResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // There are no request codes
                val files: ArrayList<MediaFile>? =
                    result.data?.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
                files?.let {
                    readExcelFile(files[0])
                } ?: showError(getString(R.string.error_read_certificate))
            }
        }

    private fun writeExcel() {
        try {
            val xlWb = XSSFWorkbook()
            val xlWs = xlWb.createSheet()

            xlWs.createRow(0).createCell(0).setCellValue("Hi Mahshid")

            val targetFile = File(excelFile)
            val outputStream = FileOutputStream(targetFile)

            xlWb.write(outputStream)
            xlWb.close()
            successOperation()
        } catch (e: Exception) {
            failureOperation(e.message)
        }
    }

    private fun readExcel() {
        try {
            val input = FileInputStream(excelFile)
            val xlWb = WorkbookFactory.create(input)
            val xlWs = xlWb.getSheetAt(0)
            yToast(xlWs.getRow(0).getCell(0).toString(), MessageStatus.INFORMATION)
        } catch (e: Exception) {
            failureOperation(e.message)
        }
    }

    private fun readExcelFile(mediaFile: MediaFile) {
        try {
//            val inputStream = FileInputStream(excelFile)
//            val xlWb = WorkbookFactory.create(inputStream)
//            val xlWs = xlWb.getSheetAt(0)
//            yToast("data: ${xlWs.getRow(0).getCell(0)}", MessageStatus.INFORMATION)
            successOperation()
        } catch (e: Exception) {
            failureOperation(e.message)
        }
    }

    private fun saveCertificateFile(certFile: MediaFile) {
        try {
            val targetPath = PATH + File.separator + "${CERTIFICATE_NAME}.cer"
            val targetFile = File(targetPath)
            if (certFile.name.extension == "cer") {
                if (targetFile.exists())
                    targetFile.delete()
                File(certFile.path).let { sourceFile ->
                    sourceFile.copyTo(File(targetPath))
                }
              restart()
            } else
                showError("فایل انتخابی معتبر نیست، لطفا فایل با پسوند 'cer.' انتخاب کنید")
        } catch (e: Exception) {
            e.printStackTrace()
            failureOperation()
        }
    }

    private val String.extension: String
        get() = substringAfterLast('.', "")

    private fun restart() {
        val mainIntent: Intent =
            IntentCompat.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_LAUNCHER)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(mainIntent)
        exitProcess(0)
    }
}