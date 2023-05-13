package ir.nikafarinegan.salefactor.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.awlrhm.modules.device.getDeviceIMEI
import ir.awlrhm.modules.device.getDeviceName
import ir.awlrhm.modules.device.getOSVersion
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.checkReadPhoneState
import ir.awlrhm.modules.extentions.checkSecurity
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.utils.OnPermissionListener
import ir.awlrhm.modules.view.ActionDialog
import ir.nikafarinegan.salefactor.BuildConfig
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.settings.isSecure
import ir.nikafarinegan.salefactor.view.dashboard.DashboardActivity
import ir.nikafarinegan.salefactor.view.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (viewModel.isLogout) {
            showLogin()
            viewModel.isLogout = false
        }else
            showSplashScreen()
    }

    private fun showSplashScreen() {
        replaceFragmentInActivity(
            R.id.container,
            SplashScreenFragment{
                checkPhoneStatePermission()
            },
            SplashScreenFragment.TAG
        )
    }

    private fun showLogin() {
        replaceFragmentInActivity(
            R.id.container,
            LoginFragment(
                object: LoginFragment.OnLoginAction{
                    override fun needRegister() {
                        gotoRegister()
                    }

                    override fun loginDone() {
                        gotoDashboard()
                    }
                }
            ),
            LoginFragment.TAG
        )
    }

    private fun gotoRegister() {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
    }

    private fun gotoDashboard() {
        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
    }

    private fun checkPhoneStatePermission() {
        checkReadPhoneState(object : OnPermissionListener {
            override fun onPermissionGranted() {
                setDeviceInformation()
                if (isSecure)
                    checkSecurity { showLogin() }
                else
                    showLogin()
            }

            override fun onPermissionDenied() {
                showPermissionActionDialog()
            }
        })
    }

    private fun showPermissionActionDialog() {
        ActionDialog.Builder()
            .setAction(MessageStatus.ERROR)
            .setTitle(getString(R.string.error))
            .setDescription(getString(R.string.set_permission_for_operations))
            .setNegative(getString(R.string.cancel)) {
                onBackPressed()
            }
            .setPositive(getString(R.string.set_permission)) {
                checkPhoneStatePermission()
            }
            .build()
            .show(supportFragmentManager, ActionDialog.TAG)
    }

    private fun setDeviceInformation() {
        viewModel.imei = getDeviceIMEI()
        viewModel.osVersion = getOSVersion()
        viewModel.deviceModel = getDeviceName() ?: ""
        viewModel.appVersion = BuildConfig.VERSION_NAME
    }

    override fun onBackPressed() {
        this.finishAffinity()
    }
}