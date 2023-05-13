package ir.nikafarinegan.salefactor.view.register

import android.content.Intent
import android.os.Bundle
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.view.base.BaseActivity
import ir.nikafarinegan.salefactor.view.dashboard.DashboardActivity
import ir.nikafarinegan.salefactor.view.login.LoginActivity

class RegisterActivity : BaseActivity() {

    override fun setup() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_register)
        super.onCreate(savedInstanceState)

        replaceFragmentInActivity(
            R.id.container,
            RegisterFragment(
                object: RegisterFragment.OnRegisterAction {
                    override fun registered() {
                        gotoDashboard()
                    }

                    override fun failedRegister() {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }
                }
            ),
            RegisterFragment.TAG
        )
    }

    private fun gotoDashboard(){
        startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            supportFragmentManager.popBackStack()

        else
            this.finish()
    }
}