package br.com.aj.message.appaiko.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aj.message.appaiko.data.L
import br.com.aj.message.appaiko.data.V
import br.com.aj.message.appaiko.databinding.PrevBusItemBinding
import br.com.aj.message.appaiko.databinding.PrevTermiItemBinding

class PrevAdapterItem : ListAdapter<PrevAdapterItem.ViewsV,RecyclerView.ViewHolder>(VDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

       return when(viewType){
           1-> MyView(PrevBusItemBinding.inflate(LayoutInflater.from(parent.context)))
           2-> MyView2(PrevTermiItemBinding.inflate(LayoutInflater.from(parent.context)))

           else -> {
               MyView(PrevBusItemBinding.inflate(LayoutInflater.from(parent.context)))
           }
       }


    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItem(position).typeView == 1){
            ( holder as MyView).item.horario.text = getItem(position).v?.t
            ( holder as MyView).item.prefixo.text = getItem(position).v?.p.toString()
        }else{
            ( holder as MyView2).item.termi.text = getItem(position).l?.lt0
            ( holder as MyView2).item.desceiTermi.text = ("${getItem(position).l?.lt1} / ${getItem(position).l?.lt0}")

        }

    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).typeView
    }

    class MyView(val item: PrevBusItemBinding) : RecyclerView.ViewHolder(item.root){

    }

    class MyView2(val item: PrevTermiItemBinding) : RecyclerView.ViewHolder(item.root){

    }

    class VDiffCallback : DiffUtil.ItemCallback<ViewsV>() {
        override fun areItemsTheSame(oldItem: ViewsV, newItem: ViewsV): Boolean {
            return  oldItem.v?.p == newItem.v?.p
        }

        override fun areContentsTheSame(oldItem: ViewsV, newItem: ViewsV): Boolean {
            return  oldItem == newItem
        }

    }

    data class ViewsV(val typeView:Int,val v :V?,val l: L?)

}