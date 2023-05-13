package ir.nikafarinegan.contractors.view.noConnection

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.PrivateViewModel
import ir.nikafarinegan.salefactor.view.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.fragment_no_connection.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Ali_Kazemi on 12/09/2021.
 */
class NoConnectionFragment: Fragment() {

    private val viewModel by viewModel<PrivateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_connection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutOffline.setOnClickListener {
            logout()
        }

       layoutWifi.setOnClickListener {
           startActivity( Intent(Settings.ACTION_WIFI_SETTINGS))
       }
        layoutMobile.setOnClickListener {
            startActivity( Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS))
        }
    }
    private fun logout() {
        val activity = activity ?: return
        viewModel.isOfflineMode = true
        activity.startActivity(Intent(activity, DashboardActivity::class.java))
    }

    companion object{
        val TAG = "${Const.APP_NAME}: ${NoConnectionFragment::class.java.simpleName}"
    }
}