package br.com.aj.message.appaiko.ui.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.aj.message.appaiko.R
import br.com.aj.message.appaiko.databinding.PrevTermiItemBinding

class SearchAdapter : ListAdapter<SearchAdapter.Item,SearchAdapter.MyView>(SearchDiff())  {

    private val unfilterList = arrayListOf<Item>()

    lateinit var clickViewPosition : (Double,Double) -> Unit

  data  class Item(
      var lat: Double,
      var long: Double,
      val content:String,
      val tag:String,
      val title:String,
      val bitmap: Int?
      ) : Parcelable {
      constructor(parcel: Parcel) : this(
          parcel.readDouble(),
          parcel.readDouble(),
          parcel.readString()!!,
          parcel.readString()!!,
          parcel.readString()!!,
          parcel.readValue(Int::class.java.classLoader) as? Int
      ) {
      }

      override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeDouble(lat)
          parcel.writeDouble(long)
          parcel.writeString(content)
          parcel.writeString(tag)
          parcel.writeString(title)
          parcel.writeValue(bitmap)
      }

      override fun describeContents(): Int {
          return 0
      }

      companion object CREATOR : Parcelable.Creator<Item> {
          override fun createFromParcel(parcel: Parcel): Item {
              return Item(parcel)
          }

          override fun newArray(size: Int): Array<Item?> {
              return arrayOfNulls(size)
          }
      }
  }


    fun submitListFilterlist(list: MutableList<Item>?) {
        list?.let { unfilterList.addAll(it) }

    }

    fun filter(str:String){
        val filterList = arrayListOf<Item>()

        if(str.isNotEmpty()) {
            filterList.addAll(
                unfilterList.filter {
                    it.title.lowercase().contains(str.lowercase()) ||
                            it.tag.lowercase().contains(str.lowercase()) ||
                            it.content.lowercase().contains(str.lowercase())
                }
            )
            submitList(filterList)
        }else{
            submitList(unfilterList)
        }

    }


    class MyView(val vi: PrevTermiItemBinding, val ctx:Context) : RecyclerView.ViewHolder(vi.root)



    class SearchDiff(): DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return  oldItem.tag == newItem.tag
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return  oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
       return MyView(PrevTermiItemBinding.inflate(LayoutInflater.from(parent.context)),parent.context)

    }

    override fun onBindViewHolder(holder: MyView, position: Int) {

       if( getItem(position).bitmap == 1 ){
           holder.vi.imageView5.setImageResource(R.drawable.ic_commute_fill0_wght400_grad0_opsz48)
       }else{
           holder.vi.imageView5.setImageResource(R.drawable.ic_directions_bus_fill0_wght400_grad0_opsz48)
       }
        holder.vi.termi.text = getItem(position).content
        holder.vi.desceiTermi.text = getItem(position).title

        holder.vi.root.setOnClickListener { clickViewPosition.invoke(getItem(position).lat,getItem(position).long) }


    }
}