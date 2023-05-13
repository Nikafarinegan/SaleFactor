package ir.nikafarinegan.salefactor.view.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.yToast
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.extentions.isConnected
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashScreenFragment(
    private val callback: () -> Unit
) : Fragment() {

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleOnClickListeners()
        Handler(Looper.getMainLooper()).postDelayed({
            showAnimation()
        }, 2700)

        Handler(Looper.getMainLooper()).postDelayed({
            checkConnection()
        }, 6500)
    }

    private fun handleOnClickListeners() {
        btnRetry.setOnClickListener {
            showTry(false)
            checkConnection()
        }
        btnOffline.setOnClickListener {
            viewModel.isOfflineMode = true
            startActivity(Intent(activity, DashboardActivity::class.java))
        }
    }

    private fun checkConnection() {
        val activity = activity ?: return
        if (activity.isConnected()) {
            viewModel.isOfflineMode = false
            callback.invoke()
        } else
            showTry(true)
    }

    private fun showAnimation() {

        txtAppName.isVisible = true
        YoYo.with(Techniques.FadeIn)
            .playOn(txtAppName)

        txtAppTitle.isVisible = true
        YoYo.with(Techniques.BounceInUp)
            .playOn(txtAppTitle)

        Handler(Looper.getMainLooper()).postDelayed({
            prcTry.isVisible = true
        }, 2500)
    }

    private fun showTry(visible: Boolean) {
        if (visible) {
            prcTry.visibility = View.GONE
            layoutTry.visibility = View.VISIBLE
            activity?.yToast(getString(R.string.no_internet), MessageStatus.ERROR)
        } else {
            prcTry.visibility = View.VISIBLE
            layoutTry.visibility = View.GONE
        }
    }

    companion object {
        val TAG = "${Const.APP_NAME} : ${SplashScreenFragment::class.java.simpleName}"
    }
}