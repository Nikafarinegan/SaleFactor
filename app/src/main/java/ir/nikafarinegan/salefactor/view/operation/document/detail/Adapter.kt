package ir.nikafarinegan.salefactor.view.operation.document.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentDetailListResponse
import kotlinx.android.synthetic.main.item_detail_goods.view.*

class Adapter(
    private val list: MutableList<WarehouseDocumentDetailListResponse.Result>,
    private val callback: OnActionListener
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_goods, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: WarehouseDocumentDetailListResponse.Result){
            itemView.txtGoodsCode.text = model.goodId.toString()
            itemView.txtDescription.text = model.goodsName
            itemView.txtUnit.text = model.unit
            itemView.txtCount.text = model.amount.toString()
            itemView.setOnClickListener {
                callback.onClick(model)
            }
            itemView.layoutEdit.setOnClickListener {
                callback.onEdit(model)
            }
            itemView.layoutDelete.setOnClickListener {
                callback.onDelete(model.wddId ?: 0)
            }
        }
    }

    interface OnActionListener{
        fun onClick(model: WarehouseDocumentDetailListResponse.Result)
        fun onEdit(model: WarehouseDocumentDetailListResponse.Result)
        fun onDelete(wddId: Long)
    }
    
}