package ir.nikafarinegan.salefactor.view.baseInformation

import android.content.Intent
import android.os.Bundle
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.utils.Const.KEY_SSID
import ir.nikafarinegan.salefactor.utils.Const.KEY_SUB_SYSTEM_NAME
import ir.nikafarinegan.salefactor.view.base.BaseActivity
import ir.nikafarinegan.salefactor.view.baseInformation.customer.CustomerFragment
import ir.nikafarinegan.salefactor.view.noConnection.NoConnectionActivity

class BaseInformationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_information)

        val ssId = intent.extras?.getInt(KEY_SSID)
        val subSystemName = intent.extras?.getString(KEY_SUB_SYSTEM_NAME)
        gotoSubSystem(ssId, subSystemName)
    }

    private fun gotoSubSystem(ssId: Int?, subSystemName: String?) {
        when(ssId){
            Const.BaseInformation.KEY_CUSTOMERS ->
                gotoCustomers(subSystemName)
            else -> {
             yToast(getString(R.string.no_item_exist), MessageStatus.ERROR)
                onBackPressed()
            }
        }
    }

    private fun gotoCustomers(subSystemName: String?) {
        replaceFragmentInActivity(
            R.id.container,
            CustomerFragment(subSystemName),
            CustomerFragment.TAG
        )
    }

    override fun showNoConnection() {
       startActivity(Intent(this, NoConnectionActivity::class.java))
    }


    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}