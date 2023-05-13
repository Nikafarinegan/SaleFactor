package ir.nikafarinegan.salefactor.view.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import ir.awlrhm.modules.enums.MessageStatus
import ir.awlrhm.modules.extentions.showError
import ir.awlrhm.modules.extentions.yToast
import ir.awlrhm.modules.view.ActionDialog
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.request.UserSmartDeviceRequest
import ir.nikafarinegan.salefactor.extentions.checkConfirmationCodeJson
import ir.nikafarinegan.salefactor.utils.Const
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.data.network.model.base.BaseGetRequest
import kotlinx.android.synthetic.main.contain_register.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class RegisterFragment(
    private val listener: OnRegisterAction
) : BaseFragment() {

    private val viewModel: RegisterViewModel by viewModel()
    private var timer: CountDownTimer? = null
    private var currentStatus: Status = Status.LOADING


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPersonnelInformation(
            BaseGetRequest().also { request ->
                request.userId = viewModel.userId
                request.typeOperation = 501
            }
        )
    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        btnSendRegisterCode.setOnClickListener {
            onStatus(Status.LOADING)
            viewModel.isExistUserSmartDevice(
                UserSmartDeviceRequest().also { request ->
                    request.userId = viewModel.userId
                    request.typeOperation = 3
                }
            )
        }
        edtRegisterCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length == 6) {
                    timer?.cancel()
                    onStatus(Status.LOADING)

                    viewModel.getCheckConfirmCode(
                        UserSmartDeviceRequest().also { request ->
                            request.jsonParameters =
                                checkConfirmationCodeJson(edtRegisterCode.text.toString().toInt())
                            request.userId = viewModel.userId
                            request.typeOperation = 2
                        }
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        btnResendCode.setOnClickListener {
            ActionDialog.Builder()
                .setTitle(getString(R.string.resend_activation_code))
                .setDescription(getString(R.string.description_waiting_activation_code))
                .setPositive(getString(R.string.resend)) {
                    onStatus(Status.TIMER_RESTARTED)
                    postUserSmartDevice()
                }
                .build()
                .show(activity.supportFragmentManager, ActionDialog.TAG)
        }
        btnBack.setOnClickListener {
            activity.onBackPressed()
        }
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.personnelResponse.observe(viewLifecycleOwner, { result ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                onStatus(Status.SUCCESS)
                if (result != null) {
                    txtUserFullName.text = viewModel.userFamily
                    txtPersonnelCode.text = viewModel.personnelId.toString()
                    txtPhoneNumber.text = result.mobile

                } else {
                    activity.yToast(
                        getString(R.string.error_personnel_information),
                        MessageStatus.ERROR
                    )
                }
            }
        })
        viewModel.userSmartDeviceByIMEIResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                it.result?.let { list ->
                    if (list.isEmpty())
                        postUserSmartDevice()
                    else
                        listener.registered()
                } ?: kotlin.run {
                    postUserSmartDevice()
                }
            }
        })
        viewModel.responseId.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (it.result != 0L) {
                    if (currentStatus == Status.TIMER_RESTARTED) {
                        prcRegister.isVisible = false
                        btnResendCode.isVisible = false
                        txtTimeView.isVisible = true
                        timer = null
                        startTimer()
                    } else {
                        onStatus(Status.SUCCESS)
                        onStatus(Status.REGISTER_CODE)
                    }
                } else
                    activity.yToast(getString(R.string.failed_operation), MessageStatus.ERROR)
            }
        })
        viewModel.checkConfirmCodeResponse.observe(viewLifecycleOwner, { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (response.result == 0L) {
                    edtRegisterCode.setText("")
                    activity.showError(getString(R.string.wrong_code))
                    onStatus(Status.WRONG_CODE)
                } else listener.registered()
            }
        })
    }

    private fun postUserSmartDevice() {
        viewModel.postUserSmartDevice(
            UserSmartDeviceRequest().also { request ->
                request.userId = viewModel.userId
                request.typeOperation = 1
            }
        )
    }

    private fun startTimer() {
        timer = object : CountDownTimer(120000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                txtTimeView.text = String.format(
                    "%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    millisUntilFinished
                                )
                            )
                )
            }

            override fun onFinish() {
                onStatus(Status.TIMER_FINISHED)
            }
        }
        timer?.start()
    }

    private fun onStatus(state: Status) {
        when (state) {
            Status.SUCCESS -> {
                loading.isVisible = false
                if (!layoutRegister.isVisible)
                    layoutRegister.isVisible = true
            }
            Status.FAILURE -> {
                viewModel.isLogout = true
                listener.failedRegister()
            }
            Status.TIMER_FINISHED -> {
                txtTimeView.isVisible = false
                btnResendCode.isVisible = true
            }

            Status.TIMER_RESTARTED -> {
                currentStatus = Status.TIMER_RESTARTED
                btnResendCode.isVisible = false
                prcRegister.isVisible = true
            }
            Status.LOADING -> {
                if (layoutRegister.isVisible) {
                    loading.isVisible = true

                } else {
                    layoutTimer.isVisible = false
                    prcRegister.isVisible = true
                }
            }
            Status.REGISTER_CODE -> {
                layoutSendCode.isVisible = false
                layoutRegisterCode.isVisible = true
                edtRegisterCode.requestFocus()
                startTimer()
            }
            Status.WRONG_CODE -> {
                loading.isVisible = false
                edtRegisterCode.setText("")
                txtTimeView.isVisible = false
                btnResendCode.isVisible = true
            }
        }
    }

    override fun handleError() {
        super.handleError()
        val activity = activity ?: return
        viewModel.error.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (loading.isVisible)
                    loading.isVisible = false

                activity.showError(it?.message)
                onStatus(Status.FAILURE)
            }
        })
        viewModel.userSmartDeviceErrorResponse.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                onStatus(Status.FAILURE)
                activity.showError(
                    it.message ?: getString(R.string.no_valid_device_information)
                )
            }
        })
        viewModel.confirmCodeError.observe(viewLifecycleOwner, {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                activity.showError(it.message ?: getString(R.string.wrong_code))
                onStatus(Status.WRONG_CODE)
            }
        })
    }


    enum class Status {
        LOADING,
        SUCCESS,
        FAILURE,
        TIMER_FINISHED,
        TIMER_RESTARTED,
        REGISTER_CODE,
        WRONG_CODE
    }

    interface OnRegisterAction {
        fun registered()
        fun failedRegister()
    }

    companion object {
        val TAG = "${Const.APP_NAME}: ${RegisterFragment::class.java.simpleName}"
    }
}