package ir.nikafarinegan.salefactor.view.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.awlrhm.modules.extentions.configBottomSheet
import ir.awlrhm.modules.extentions.convertToBitmap
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.UserSmartDeviceResponse
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.bottom_sheet_profile.*
import kotlinx.android.synthetic.main.item_device.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetProfile(
    private val listUserSmartDevice: List<UserSmartDeviceResponse.Result>,
    private val listener: OnProfileActionListener
) : BottomSheetDialogFragment() {

    private val viewModel: DashboardViewModel by viewModel()

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val activity = activity ?: return

        val view = View.inflate(context, R.layout.bottom_sheet_profile, null)
        dialog.setContentView(view)

        activity.configBottomSheet(view, 1f)
        dialog.handleOnClickListeners()
        dialog.setup()
    }

    private fun Dialog.setup() {
        val activity = activity ?: return

        this.txtUserFamily.text = viewModel.userFamily
        this.txtUserPosition.text = viewModel.userPosition
        if (viewModel.userThumbnail.isNotEmpty())
           try {
               Glide
                   .with(activity)
                   .load(convertToBitmap(viewModel.userThumbnail))
                   .centerCrop()
                   .apply(RequestOptions())
                   .error(R.drawable.icon_account)
                   .into(this.imgUserProfile)
           }catch (ex: Exception){}

    }

    private fun Dialog.handleOnClickListeners() {
        this.btnClose.setOnClickListener { dismiss() }
        this.layoutChangePassword.setOnClickListener {
            listener.onChangePassword()
            dismiss()
        }
        this.layoutNotifications.setOnClickListener {
            listener.onMessages()
        }
        this.layoutSecuritySettings.setOnClickListener {
            listener.onSecuritySettings()
        }
        this.layoutMyProfile.setOnClickListener {
            listener.onUserProfile()
        }
        this.layoutDevices.setOnClickListener {
            if (this.layoutItemDevices.isVisible)
                this.layoutItemDevices.isVisible = false
            else {
                this.layoutItemDevices.isVisible = true
                if (this.layoutItemDevices.childCount == 0)
                    showUserSmartDevices()
            }
        }
    }

    private fun showUserSmartDevices() {
        val activity = activity ?: return
        val dialog = dialog ?: return

        listUserSmartDevice.forEach { model ->
            val view = LayoutInflater.from(activity)
                .inflate(R.layout.item_device, dialog.layoutItemDevices, false)
            view.txtUserPhoneNo.text = model.mobileNo
            view.txtUserPhoneModel.text = model.model
            dialog.layoutItemDevices.addView(view)
        }
    }

    interface OnProfileActionListener {
        fun onUserProfile()
        fun onChangePassword()
        fun onMessages()
        fun onSecuritySettings()
    }

    companion object {
        val TAG = "${Const.APP_NAME} ${BottomSheetProfile::class.java.simpleName}"
    }
}