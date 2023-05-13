package ir.nikafarinegan.salefactor.view.operation.document.document

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.awlrhm.modules.extentions.convertListToString
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.WarehouseDocumentListResponse
import kotlinx.android.synthetic.main.item_document.view.*

class Adapter(
    private val callback: OnActionListener
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    private val list: MutableList<WarehouseDocumentListResponse.Result> = mutableListOf()

    fun setSource(items: MutableList<WarehouseDocumentListResponse.Result>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_document, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: WarehouseDocumentListResponse.Result){
            model.wdSubject?.let {
                itemView.txtDocument.text = convertListToString(it.split(','))
            }
            itemView.setOnClickListener {
                callback.onClick(model)
            }
            itemView.layoutEdit.setOnClickListener {
                callback.onEdit(model)
            }
            itemView.layoutDelete.setOnClickListener {
                callback.onDelete(model.wdId ?: 0)
            }
        }
    }

    interface OnActionListener{
        fun onClick(result: WarehouseDocumentListResponse.Result)
        fun onEdit(result: WarehouseDocumentListResponse.Result)
        fun onDelete(wdId: Long)
    }
}