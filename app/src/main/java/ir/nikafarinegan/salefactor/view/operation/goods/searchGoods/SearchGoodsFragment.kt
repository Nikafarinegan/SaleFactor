package ir.nikafarinegan.salefactor.view.operation.goods.searchGoods
/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ir.awlrhm.modules.enums.Status
import ir.awlrhm.modules.utils.OnStatusListener
import ir.awlrhm.modules.enums.MessageStatus
import ir.awrhm.modules.extensions.hideKeyboard
import ir.awrhm.modules.extensions.showKeyboard
import ir.awrhm.modules.extensions.yToast
import ir.nikafarinegan.salefactor.view.base.BaseFragment
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.view.operation.goods.AddGoodsDialog
import ir.nikafarinegan.salefactor.view.operation.goods.GoodsViewModel
import kotlinx.android.synthetic.main.fragment_search_goods.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchGoodsFragment(
    private val wddId: Long,
    private val wdId: Long,
    private val callback: () -> Unit
) : BaseFragment(), OnStatusListener {

    private val viewModel by viewModel<GoodsViewModel>()
    private var counter: Int = 1

    override fun setup() {
        edtSearch.requestFocus()
        activity?.showKeyboard()
        rclSearch.layoutManager(LinearLayoutManager(activity))
            .theme(R.color.colorAccent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_goods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun handleOnClickListeners() {
        val activity = activity ?: return

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                search()
            true
        }
        btnBack.setOnClickListener {
            activity.hideKeyboard(layoutSearch)
            activity.onBackPressed()
        }
        btnSearch.setOnClickListener { search() }
    }

    override fun handleObservers() {
        val activity = activity ?: return

        viewModel.searchGoodsResponse.observe(viewLifecycleOwner,{ response ->
            response.result?.let { list ->
                if (list.size > 0) {
                    rclSearch.view?.adapter = Adapter(list) {
                        edtSearch.setText("")

                        AddGoodsDialog(it, wdId) {
                            callback.invoke()
                        }.show(
                            activity.supportFragmentManager,
                            AddGoodsDialog.TAG
                        )
                    }

                } else
                    status(Status.FAILURE)

            } ?: kotlin.run {
                status(Status.FAILURE)
            }
        })
    }

    private fun search() {
        val activity = activity ?: return

        activity.hideKeyboard(layoutSearch)
        val search = edtSearch.text.toString()
        if (search.isNotEmpty()) {
            showSearchLayout()
            viewModel.searchGoodsList(
                search,
                wddId,
                viewModel.financialYear,
                counter
            )
        } else
            activity.yToast(
                getString(R.string.fill_search_key),
                MessageStatus.ERROR
            )
    }

    private fun showSearchLayout() {
        logo_search.visibility = View.GONE
        rclSearch.visibility = View.VISIBLE
        rclSearch.showLoading()
    }

    override fun status(status: Status) {
        when(status){
           Status.LOADING ->{

            }
            else ->{
                rclSearch.showNoData()
            }
        }
    }

    override fun handleError() {
        viewModel.error.observe(viewLifecycleOwner,{
            activity?.yToast(
                it.message ?: getString(R.string.response_error_all),
                MessageStatus.ERROR
            )
        })
    }

*/