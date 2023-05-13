package ir.nikafarinegan.salefactor.view.baseInformation.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.nikafarinegan.salefactor.R
import ir.nikafarinegan.salefactor.data.network.model.response.CustomerResponse
import kotlinx.android.synthetic.main.item_customer.view.*

class Adapter(
    private val list: List<CustomerResponse.Result>
): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) = holder.onBind(list[position])

    override fun getItemCount(): Int = list.size

    inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(model: CustomerResponse.Result){
            itemView.txtCustomer.text = model.ccNote
            itemView.txtCustomerTitle.text = model.customerTitle
            itemView.txtCustomerCode.text = model.customerId.toString()
            itemView.txtCustomerParentCode.text = model.customerParentId.toString()
            itemView.txtRegisteredDate.text = model.customerUpdateDate
        }
    }
}