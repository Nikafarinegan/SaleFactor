package ir.nikafarinegan.salefactor.view.base

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ir.nikafarinegan.salefactor.extentions.isConnected
import ir.nikafarinegan.salefactor.settings.isSecure
import ir.nikafarinegan.salefactor.view.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity : AppCompatActivity() {

    private val viewModel by viewModel<PrivateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSecure)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        setup()
        handleOnClickListeners()
        handleObservers()
        handleError()
    }

    fun logout() {
        viewModel.isLogout = true
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun checkCurrentNetwork() {
        if (!viewModel.isOfflineMode)
            if (!isConnected()) {
                showNoConnection()
            }
    }

    open fun setup() {}
    open fun handleError() {
        checkCurrentNetwork()
    }
    open fun handleOnClickListeners() {}
    open fun handleObservers() {}

    open fun showNoConnection(){}
}