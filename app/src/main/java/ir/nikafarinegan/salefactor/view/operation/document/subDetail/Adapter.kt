package ir.nikafarinegan.salefactor.view.operation.document.subDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentSubDetailResponse
import kotlinx.android.synthetic.main.item_sub_detail_goods.view.*

class Adapter(
    private val list: MutableList<WarehouseDocumentSubDetailResponse.Result>,
    private val callback: OnActionListener
) : RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sub_detail_goods, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: WarehouseDocumentSubDetailResponse.Result) {
            itemView.txtGoodsCode.text = model.goodsId.toString()
            itemView.txtDescription.text = model.goodsName
            itemView.txtSerialNum.text = model.serialNum
            itemView.txtCount.text =
                when {
                    model.outputAmount != 0 -> model.outputAmount.toString()
                    model.inputAmount != 0 -> model.inputAmount.toString()
                    else -> model.returnAmount.toString()
                }
            itemView.btnDelete.setOnClickListener {
                callback.onDelete(model.wdsdId ?: 0)
            }
        }
    }

    interface OnActionListener {
        fun onDelete(wdsdId: Long)
    }

}