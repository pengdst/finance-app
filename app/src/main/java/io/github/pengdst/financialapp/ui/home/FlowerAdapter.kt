package io.github.pengdst.financialapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.pengdst.financialapp.R

class FlowerAdapter : RecyclerView.Adapter<FlowerAdapter.ViewHolder>() {

    private val flowers = mutableListOf<Flower>()
    private var _itemClick: FlowerAdapter.ItemClick<Flower>? = null

    fun submitList(newFlowers: List<Flower>) {
        flowers.clear()
        flowers.addAll(newFlowers)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(itemCLick: ItemClick<Flower>) {
        _itemClick = itemCLick
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFlowerName = view.findViewById<TextView>(R.id.tv_flower_name)
        val imgFlower = view.findViewById<ImageView>(R.id.img_flower)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_flower, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flower = flowers[position]

        holder.apply {
            tvFlowerName.text = flower.flowerName

            Glide.with(holder.itemView)
                .load(flower.flowerImageUrl)
                .into(imgFlower)

            _itemClick?.let {itemClick->
                itemView.setOnClickListener {
                    itemClick.onItemClick(it, flower, position)
                }
            }
        }
    }

    override fun getItemCount() = flowers.size

    interface ItemClick<T> {
        fun onItemClick(view: View, data: T, position: Int)
    }
}