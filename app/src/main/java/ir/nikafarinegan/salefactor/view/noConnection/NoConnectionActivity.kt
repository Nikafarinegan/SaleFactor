package ir.nikafarinegan.salefactor.view.noConnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.awlrhm.modules.extentions.addFragmentInActivity
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.view.ActionDialog
import ir.nikafarinegan.contractors.view.noConnection.NoConnectionFragment
import ir.nikafarinegan.salefactor.R

class NoConnectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_connection)

        replaceFragmentInActivity(
            R.id.container,
            NoConnectionFragment(),
            NoConnectionFragment.TAG
        )
    }

    override fun onBackPressed() {
        ActionDialog.Builder()
            .setTitle(getString(R.string.exit))
            .setDescription(getString(R.string.do_you_want_exit))
            .setPositive(getString(R.string.yes)) {
                this.finishAffinity()
            }
            .setNegative(getString(R.string.no)){}
            .build()
            .show(supportFragmentManager, ActionDialog.TAG)
    }
}