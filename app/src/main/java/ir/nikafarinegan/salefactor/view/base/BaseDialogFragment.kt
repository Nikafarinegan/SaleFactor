package ir.nikafarinegan.salefactor.view.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment: DialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        handleObservers()
        handleOnClickListeners()
        handleError()
    }

    open fun setup(){}
    open fun handleObservers(){}
    open fun handleOnClickListeners(){}
    open fun handleError(){}

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}