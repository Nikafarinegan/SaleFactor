package ir.nikafarinegan.salefactor.view.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.extentions.*
import ir.awlrhm.modules.fingerprint.FingerPrint
import ir.awlrhm.modules.log.CrashLog
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.changesDialog.ChangesListDialog
import ir.nikafarinegan.salefactor.BuildConfig
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.PostUnhandledExceptionRequest
import ir.nikafarinegan.salefactor.extentions.isConnected
import ir.nikafarinegan.salefactor.extentions.recoverPasswordJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.view.dashboard.DashboardActivity
import ir.nikafarinegan.salefactor.view.dialog.RegisterDataDialog
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment(
    private val listener: OnLoginAction
) : BaseFragment(), OnStatusListener {

    private val viewModel: LoginViewModel by viewModel()

    private lateinit var fingerPrint: FingerPrint
    private var isSuccessFingerPrint: Boolean = false

    private var logCounter = 0
    private var deviceStatus = 0
    private var logger: CrashLog? = null

    override fun setup() {
        val activity = activity ?: return

        if (!activity.isConnected()) {
            activity.yToast(getString(R.string.no_internet), MessageStatus.ERROR)
            btnOffline.isVisible = true
            btnFingerprint.isVisible = false
        }
        fingerPrint = FingerPrint(
            activity as LoginActivity,
            object : FingerPrint.OnActionListener {
                override fun onSuccess() {
                    isSuccessFingerPrint = true
                    login()
                }
            }
        )
        if (
            viewModel.username.isNotEmpty()
            && viewModel.password.isNotEmpty()
            && !viewModel.isOfflineMode
        ) {
            if (fingerPrint.hasFingerPrint)
                btnFingerprint.isVisible = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtVersion.text = BuildConfig.VERSION_NAME

        if (viewModel.rememberMe) {
            chbRemember.isChecked = true
            edtPassword.setText(viewModel.password)
            edtUsername.setText(viewModel.username)

        } else {
            chbRemember.isChecked = false
            viewModel.username = ""
            viewModel.password = ""
            edtPassword.setText("")
            edtUsername.setText("")
        }
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return
        edtPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                login()
            }
            true
        }
        btnLogin.setOnClickListener {
            login()
        }
        btnOffline.setOnClickListener {
            viewModel.isOfflineMode = true
            startActivity(Intent(activity, DashboardActivity::class.java))
        }
        btnForgotPassword.setOnClickListener {
            RegisterDataDialog(
                getString(R.string.enter_username),
                getString(R.string.send_password)
            ) { userId ->
                activity.yToast(
                    getString(R.string.send_password_your_number),
                    MessageStatus.SUCCESS
                )

                viewModel.postPasswordRecover(
                    BaseGetRequest().also {request ->
                        request.jsonParameters = recoverPasswordJson(userId )
                        request.typeOperation = 3
                    })

            }.show(activity.supportFragmentManager, RegisterDataDialog.TAG)
        }
        layoutVersion.setOnClickListener {
            ChangesListDialog(viewModel.getChangesList())
                .show(activity.supportFragmentManager, ChangesListDialog.TAG)
        }
        headerLogo.setOnClickListener {
            logCounter++
            if (logCounter == 5) {
                logCounter = 0
                try {
                    val logger = CrashLog(activity)
                    if (logger.isExistCrashLog()) {
                        logger.showCrashLog()
                        logger.copyToClipboard()
                    }
                } catch (e: Exception) {
                    activity.yToast(
                        getString(R.string.no_crash_recorded),
                        MessageStatus.ERROR
                    )
                }
            }
        }
        btnFingerprint.setOnClickListener {
            fingerPrint.show()
        }
    }

    private fun login() {
        val activity = activity ?: return

        activity.hideKeyboard(btnLogin)
        val username = convertToEnglishDigits(edtUsername.text.toString())
        val password = convertToEnglishDigits(edtPassword.text.toString())
        if (username.isNotEmpty() && password.isNotEmpty()) {
            onStatus(Status.LOADING)
            viewModel.login(username, password)
        } else
            activity.showFlashbar(
                getString(R.string.error),
                getString(R.string.invalid_user_pass),
                MessageStatus.ERROR
            )
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.loginResponse.observe(viewLifecycleOwner, { response ->
            response.result?.let { result ->
                result.accessToken?.let { token ->
                    viewModel.clearCash()
                    viewModel.rememberMe = chbRemember.isChecked
                    viewModel.accessToken = token
                    viewModel.userId = result.userId ?: 0
                    viewModel.username = edtUsername.text.toString()
                    viewModel.password = edtPassword.text.toString()
                }
                viewModel.isOfflineMode = false
                deviceStatus = result.deviceStatus ?: 0
                reportLogError()
            }
        })
        viewModel.passwordResponse.observe(viewLifecycleOwner, { response ->
            response.result?.let {
                if (it != 0L)
                    activity.successOperation(response.message ?: getString(R.string.success_operation))
                else
                    activity.showError(response.message ?: getString(R.string.failed_operation))
            }
        })
        viewModel.downloadVersionResponse.observe(viewLifecycleOwner, {
            onStatus(Status.SUCCESS)
            ActionDialog.Builder()
                .setTitle(getString(R.string.new_version))
                .setDescription(getString(R.string.new_version_exist_caffe_bazaar))
                .setPositive(getString(R.string.install_new_version)) {
//                    downloadNewVersion(response.result?.downloadUrl)
                    if (isExistStoreApp)
                        gotoStoreApp()
                    else
                        gotoStoreBrowser()
                }
                .build()
                .show(activity.supportFragmentManager, ActionDialog.TAG)
        })
        viewModel.responseId.observe(viewLifecycleOwner, {
            if (it.result != 0L)
                logger?.deleteCrashes()
            continueLogin()
        })
    }

    private fun continueLogin() {
        onStatus(Status.SUCCESS)
        if (deviceStatus == 0)
            listener.needRegister()
        else
            listener.loginDone()
    }

    private fun reportLogError() {
        val activity = activity ?: return
        try {
            logger = CrashLog(activity)
            logger?.let { logger ->
                if (logger.isExistCrashLog()) {
                    viewModel.postUnhandledException(
                        PostUnhandledExceptionRequest().also { request ->
                            request.formPlace = logger.crashLogs[0].placeOfCrash
                            request.reasonOfCrash = logger.crashLogs[0].reasonOfCrash
                            request.stackTrace = logger.fullCrashLog
                            request.dateTime = logger.crashLogs[0].date.toString()
                            request.registerDate = viewModel.registerDate

                        }
                    )
                }
            }
        } catch (e: Exception) {
            continueLogin()
        }
    }

    private fun gotoStoreApp() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("bazaar://details?id=" + Const.PACKAGE_NAME)
        intent.setPackage("com.farsitel.bazaar")
        startActivity(intent)
    }

    private fun gotoStoreBrowser() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("bazaar://details?id=\" " + Const.PACKAGE_NAME)
            )
        )
    }

    private val isExistStoreApp: Boolean
        get() {
            val activity = activity ?: return false
            val packageManager = activity.packageManager
            return isPackageInstalled("com.farsitel.bazaar", packageManager)
        }

    override fun onStatus(status: Status) {
        when (status) {
            Status.LOADING -> {
                prcDetail.isVisible = true
            }
            else -> {
                prcDetail.isVisible = false
            }
        }
    }

    override fun handleError() {
        viewModel.error.observe(viewLifecycleOwner, {
            onStatus(Status.FAILURE)
            activity?.showFlashbar(
                getString(R.string.error),
                it?.message ?: getString(R.string.response_error),
                MessageStatus.ERROR
            )
        })
        viewModel.errorUnhandledException.observe(viewLifecycleOwner, {
            continueLogin()
        })
    }


    interface OnLoginAction {
        fun needRegister()
        fun loginDone()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${LoginFragment::class.java.simpleName}"
    }
}