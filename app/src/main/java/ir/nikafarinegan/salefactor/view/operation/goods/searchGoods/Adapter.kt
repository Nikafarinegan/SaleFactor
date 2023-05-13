package ir.nikafarinegan.salefactor.view.operation.goods.searchGoods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.WHSGoodsListResponse
import kotlinx.android.synthetic.main.item_goods.view.*

class Adapter(
    private var list: MutableList<WHSGoodsListResponse.Result>,
    private val callback: (WHSGoodsListResponse.Result) -> Unit
) : RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(model: WHSGoodsListResponse.Result) {
            itemView.txtGoodsCode.text = model.goodsId
            itemView.txtWarehouseName.text = model.warehouseName
            itemView.txtDescription.text = model.goodsName
            itemView.setOnClickListener {
                callback.invoke(model)
            }
        }
    }
}