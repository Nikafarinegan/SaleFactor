package ir.nikafarinegan.salefactor.view.dashboard.item

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.awlrhm.modules.extentions.convertToBitmap
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.SubSystemResponse
import kotlinx.android.synthetic.main.items.view.*

class ItemsAdapter(
    private val list: MutableList<SubSystemResponse.Result>,
    private val callback:(Int, String) -> Unit
): RecyclerView.Adapter<ItemsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) =
        holder.onBind(list[position])

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: SubSystemResponse.Result){

            itemView.txtNameItems.text = model.name
            itemView.imgItems.setImageBitmap(convertToBitmap(model.body ?: ""))
            itemView.imgItems.setColorFilter(ContextCompat.getColor(itemView.context,
                R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
            itemView.setOnClickListener {
                callback.invoke(model.ssId ?: 0, model.name ?: "")
            }
        }
    }
}