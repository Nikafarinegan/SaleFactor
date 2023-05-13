package ir.nikafarinegan.salefactor.view.password

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import ir.awlrhm.modules.extentions.addFragmentInActivity
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.view.ActionDialog
import ir.nikafarinegan.contractors.view.noConnection.NoConnectionFragment
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.extentions.changePasswordJson
import ir.nikafarinegan.salefactor.view.base.BaseActivity
import ir.nikafarinegan.salefactor.view.login.LoginActivity
import ir.nikafarinegan.salefactor.view.noConnection.NoConnectionActivity
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.contain_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordActivity : BaseActivity() {

    private val viewModel: ChangePasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }

    override fun handleObservers() {
        viewModel.responseId.observe(this, { response ->
            btnDone.loading(false)
            if (lifecycle.currentState == Lifecycle.State.RESUMED) {
                response.result?.let {
                    if (it != 0L) {
                        ActionDialog.Builder()
                            .setTitle(getString(R.string.success))
                            .setDescription(
                                response.message ?: getString(R.string.success_operation)
                            )
                            .setPositive(getString(R.string.login_page)) {
                                viewModel.isLogout = true
                                startActivity(
                                    Intent(
                                        this@ChangePasswordActivity,
                                        LoginActivity::class.java
                                    )
                                )
                            }
                            .setCancelable(false)
                            .build()
                            .show(supportFragmentManager, ActionDialog.TAG)
                    } else
                        showError(response.message)

                } ?: kotlin.run {
                    showError(response.message)
                }
            }
        })
    }

    override fun handleOnClickListeners() {
        btnBack.setOnClickListener {
            onBackPressed()
        }
        btnDone.setOnClickListener {
            if (isValid) {
                btnDone.loading(true)
                viewModel.postChangePassword(
                    BaseGetRequest().also { request ->
                        request.jsonParameters = changePasswordJson(
                            edtNewPassword.text.toString(),
                            edtConfirmPassword.text.toString(),
                            edtCurrentPassword.text.toString()
                        )
                        request.userId = viewModel.userId
                        request.typeOperation = 2
                    }
                )
            } else {
                btnDone.loading(false)
                showError(getString(R.string.fill_all_blanks))
            }
        }
    }

    override fun showNoConnection() {
        startActivity(Intent(this, NoConnectionActivity::class.java))
    }

    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            btnDone.loading(false)
            showError(
                it?.message ?: getString(R.string.response_error)
            )
        })
    }

    private val isValid: Boolean
        get() {

            return edtCurrentPassword.text.toString().isNotEmpty() &&
                    edtNewPassword.text.toString().isNotEmpty() &&
                    edtConfirmPassword.text.toString().isNotEmpty()
        }
}
