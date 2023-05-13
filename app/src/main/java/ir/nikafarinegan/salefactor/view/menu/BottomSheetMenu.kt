package ir.nikafarinegan.salefactor.view.menu

import android.app.Dialog
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.awlrhm.modules.extentions.configBottomSheet
import ir.awlrhm.modules.extentions.convertToBitmap
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.bottom_sheet_menu.*
import kotlinx.android.synthetic.main.bottom_sheet_menu.btnClose
import kotlinx.android.synthetic.main.bottom_sheet_menu.imgUserProfile
import kotlinx.android.synthetic.main.bottom_sheet_menu.txtUserFamily
import kotlinx.android.synthetic.main.bottom_sheet_menu.txtUserPosition
import kotlinx.android.synthetic.main.bottom_sheet_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetMenu(
    private val listener: OnMenuActionListener
) : BottomSheetDialogFragment() {

    private val viewModel: DashboardViewModel by viewModel()
    private var isShown = false

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val activity = activity ?: return

        val view = View.inflate(context, R.layout.bottom_sheet_menu, null)
        dialog.setContentView(view)

        activity.configBottomSheet(view, 1f)
        dialog.handleOnClickListeners()
        dialog.setup()
    }

    private fun Dialog.setup(){
        val activity = activity ?: return

        this.txtUserFamily.text = viewModel.userFamily
        this.txtUserPosition.text = viewModel.userPosition
        if (viewModel.userThumbnail.isNotEmpty()) {
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
    }

    private fun Dialog.handleOnClickListeners() {
        this.layoutReminder.setOnClickListener {
            listener.onReminder()
            dismiss()
        }

        this.layoutSettings.setOnClickListener {
            listener.onSettings()
            dismiss()
        }
        this.layoutReport.setOnClickListener {
            listener.onReportBug()
            dismiss()
        }
        this.layoutExit.setOnClickListener {
            listener.onLogOut()
            dismiss()
        }
        this.btnClose.setOnClickListener { dismiss() }
    }



    interface OnMenuActionListener {
        fun onReminder()
        fun onSettings()
        fun onLogOut()
        fun onReportBug()
    }

    companion object {
        val TAG = "${Const.APP_NAME} " + BottomSheetMenu::class.java.simpleName
    }
}