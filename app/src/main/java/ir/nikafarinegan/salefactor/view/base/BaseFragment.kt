package ir.nikafarinegan.salefactor.view.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ir.awlrhm.modules.enums.Status


abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        handleOnClickListeners()
        handleObservers()
        handleError()
    }

    fun logout(){
        (activity as BaseActivity).logout()
    }

    private fun checkCurrentNetwork(){
        (activity as BaseActivity).checkCurrentNetwork()
    }

    open fun setup(){}
    open fun handleOnClickListeners(){}
    open fun handleObservers(){}
    open fun handleError(){
        checkCurrentNetwork()
    }
}