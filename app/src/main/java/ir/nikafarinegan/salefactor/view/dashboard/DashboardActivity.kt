package ir.nikafarinegan.salefactor.view.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import ir.awlrhm.areminder.settings.KEY_REMINDER
import ir.awlrhm.areminder.view.reminder.ReminderActivity
import ir.awlrhm.areminder.view.reminder.model.ReminderBindDataModel
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.configProgressbar
import ir.awlrhm.modules.extentions.failureOperation
import ir.awlrhm.modules.extentions.replaceFragmentInActivity
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.models.ItemModel
import ir.awlrhm.modules.view.ActionDialog
import ir.awlrhm.modules.view.ChooseDialog
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import ir.nikafarinegan.salefactor.data.network.model.request.FinancialYearRequest
import ir.nikafarinegan.salefactor.data.network.model.request.PostUnhandledExceptionRequest
import ir.nikafarinegan.salefactor.data.network.model.request.UserSmartDeviceRequest
import ir.nikafarinegan.salefactor.data.network.model.response.UserSmartDeviceResponse
import ir.nikafarinegan.salefactor.settings.HOST_NAME
import ir.nikafarinegan.salefactor.view.base.BaseActivity
import ir.nikafarinegan.salefactor.view.dialog.RegisterDataDialog
import ir.nikafarinegan.salefactor.view.menu.BottomSheetMenu
import ir.nikafarinegan.salefactor.view.noConnection.NoConnectionActivity
import ir.nikafarinegan.salefactor.view.password.ChangePasswordActivity
import ir.nikafarinegan.salefactor.view.profile.BottomSheetProfile
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.contain_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {

    private val viewModel by viewModel<DashboardViewModel>()
    private var homeMenu = false
    private var list: MutableList<ItemModel> = mutableListOf()

    override fun setup() {
        setTitle()
        configAppbar()
        configProgressbar(prcFinancialYear, R.color.white)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_dashboard)
        super.onCreate(savedInstanceState)

        if (!viewModel.isOfflineMode
            && viewModel.userFamily.isEmpty()
            && viewModel.userPosition.isEmpty()
        ) {
            loading.isVisible = true
            viewModel.getPersonnelInformation(
                BaseGetRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 501
                }
            )

        } else {
            loading.isVisible = false
            gotoDashboard()
            configBottomNavigation()
        }
    }

    override fun handleOnClickListeners() {
        btnUser.setOnClickListener {
            if (viewModel.isOfflineMode) {
                callProfileMenu(mutableListOf())

            } else {
                btnUser.loading(true)
                viewModel.getUserSmartDevice(
                    UserSmartDeviceRequest().also { request ->
                        request.userId = viewModel.userId
                        request.typeOperation = 101
                    }
                )
            }
        }
        layoutMenu.setOnClickListener {
            loading(true)
            showFinancialYearDialog()
        }
    }

    override fun handleObservers() {
        viewModel.financialYearResponse.observe(this, {
            it.result?.let { listFinancial ->
                if (listFinancial.size > 0) {
                    listFinancial.forEach { item ->
                        list.add(ItemModel(item.id ?: 0, item.name ?: ""))
                    }
                    showFinancialYearDialog()
                }
            } ?: kotlin.run {
                loading(false)
                yToast(getString(R.string.response_error), MessageStatus.ERROR)
            }
        })
        viewModel.userSmartDeviceResponse.observe(this, {
            if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                btnUser.loading(false)
                callProfileMenu(it.result)
            }
        })

        viewModel.personnelResponse.observe(this, { result ->
            loading.isVisible = false
            if (result) {
                gotoDashboard()
                configBottomNavigation()
            } else {
                yToast(getString(R.string.error_personnel_information), MessageStatus.ERROR)
                logout()
            }
        })
        viewModel.responseId.observe(this, {
            loading.isVisible = false
            if (it.result != 0L) {
                ActionDialog.Builder()
                    .setTitle(getString(R.string.success))
                    .setDescription(getString(R.string.sent_your_report_thanks))
                    .setPositive(getString(R.string.close)) {}
                    .build()
                    .show(supportFragmentManager, ActionDialog.TAG)
            } else
                failureOperation(it.message)
        })
    }

    private fun callProfileMenu(list: List<UserSmartDeviceResponse.Result>?) {
        BottomSheetProfile(
            list ?: mutableListOf(),
            object : BottomSheetProfile.OnProfileActionListener {
                override fun onUserProfile() {
                    yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
                }

                override fun onChangePassword() {
                    startActivity(
                        Intent(
                            this@DashboardActivity,
                            ChangePasswordActivity::class.java
                        )
                    )
                }

                override fun onMessages() {
                    yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
                }

                override fun onSecuritySettings() {
                    yToast(getString(R.string.in_next_version), MessageStatus.INFORMATION)
                }
            }).show(supportFragmentManager, BottomSheetProfile.TAG)
    }


    private fun showFinancialYearDialog() {
        loading(false)

        if (list.size > 0)
            ChooseDialog(
                list,
                R.color.black,
                R.color.colorPrimary
            ) {
                viewModel.financialYearId = it.id.toInt()
                setTitle()
            }.show(supportFragmentManager, ChooseDialog.TAG)
        else
            viewModel.getFinancialYearList(
                FinancialYearRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 10
                }
            )
    }

    @SuppressLint("SetTextI18n")
    private fun setTitle() {
        txtTitle.text =
            "${getString(R.string.app_name)} (${viewModel.financialYearId})"
    }

    private fun configAppbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.elevation = 0f
    }

    private fun gotoDashboard() {
        bnvDashboard.setActiveItem(2)
        replaceFragmentInActivity(
            R.id.container,
            DashboardFragment(),
            DashboardFragment.TAG
        )
        homeMenu = true
    }

    private fun configBottomNavigation() {
        bnvDashboard.onItemSelected = {
            when (it) {
                0 -> {
                    homeMenu = false
                    replaceFragmentInActivity(
                        R.id.container,
                        ReportsDashboardFragment(),
                        ReportsDashboardFragment.TAG
                    )
                }

                1 -> {
                    homeMenu = false
                    replaceFragmentInActivity(
                        R.id.container,
                        WarehouseHandlingDashboardFragment(),
                        WarehouseHandlingDashboardFragment.TAG
                    )
                }

                3 -> {
                    homeMenu = false
                    replaceFragmentInActivity(
                        R.id.container,
                        OperationDashboardFragment(),
                        OperationDashboardFragment.TAG
                    )
                }
                4 -> {
                    homeMenu = false
                    replaceFragmentInActivity(
                        R.id.container,
                        BaseInformationFragment(),
                        BaseInformationFragment.TAG
                    )
                }
                else -> {
                    gotoDashboard()
                }
            }
        }
    }

    private fun loading(visible: Boolean) {
        if (visible) {
            txtTitle.visibility = View.GONE
            prcFinancialYear.visibility = View.VISIBLE
        } else {
            txtTitle.visibility = View.VISIBLE
            prcFinancialYear.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                showMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMenu() {
        BottomSheetMenu(
            object : BottomSheetMenu.OnMenuActionListener {

                override fun onReminder() {
                    gotoReminder()
                }

                override fun onSettings() {
                    yToast(
                        getString(R.string.in_next_version),
                        MessageStatus.INFORMATION
                    )
                }

                override fun onLogOut() {
                    logout()
                }

                override fun onReportBug() {
                    RegisterDataDialog(
                        getString(R.string.enter_bug_description),
                        getString(R.string.send_report)
                    ) {
                        loading.isVisible = true
                        viewModel.postUnhandledException(
                            PostUnhandledExceptionRequest().also { request ->
                                request.dateTime = viewModel.currentDate
                                request.reasonOfCrash = it
                                request.stackTrace = it
                                request.formPlace = it
                                request.registerDate = viewModel.registerDate
                                request.userId = viewModel.userId
                                request.financialYearId = viewModel.financialYearId
                            }
                        )
                    }.show(supportFragmentManager, RegisterDataDialog.TAG)
                }
            }).show(supportFragmentManager, BottomSheetMenu.TAG)
    }

    private fun gotoReminder() {
        val model = ReminderBindDataModel(
            token = viewModel.accessToken,
            hostName = HOST_NAME,
            personnelId = viewModel.personnelId,
            userId = viewModel.userId,
            imei = viewModel.imei,
            osVersion = viewModel.osVersion,
            deviceModel = viewModel.deviceModel,
            appVersion = viewModel.appVersion
        )

        val intent = Intent(this@DashboardActivity, ReminderActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(KEY_REMINDER, model)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun showNoConnection() {
        startActivity(Intent(this, NoConnectionActivity::class.java))
    }


    override fun handleError() {
        super.handleError()
        viewModel.error.observe(this, {
            ActionDialog.Builder()
                .setAction(MessageStatus.ERROR)
                .setTitle(getString(R.string.error))
                .setDescription(it?.message ?: getString(R.string.response_error_all))
                .setNegative(getString(R.string.understand)) {}
                .build()
                .show(supportFragmentManager, ActionDialog.TAG)
        })
        viewModel.errorUnhandledException.observe(this, {
            ActionDialog.Builder()
                .setAction(MessageStatus.ERROR)
                .setTitle(getString(R.string.error))
                .setDescription(it?.message ?: getString(R.string.error_post_unhandledException))
                .setNegative(getString(R.string.understand)) {}
                .build()
                .show(supportFragmentManager, ActionDialog.TAG)
        })
    }

    override fun onBackPressed() {
        if (!homeMenu) {
            gotoDashboard()

        } else
            ActionDialog.Builder()
                .setTitle(getString(R.string.exit))
                .setDescription(getString(R.string.do_you_want_exit))
                .setPositive(getString(R.string.yes)) {
                    this.finishAffinity()
                }
                .setNegative(getString(R.string.no)) {}
                .build()
                .show(supportFragmentManager, ActionDialog.TAG)
    }
}
