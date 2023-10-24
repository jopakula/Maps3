package com.example.maps3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maps3.databinding.ListItemBinding
import com.example.maps3.retrofit.Product


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList: List<Product> = emptyList()

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.id.text = product.id
            binding.data.text = product.data
            binding.latitude.text = product.id
            binding.longitude.text = product.data
            binding.mac.text = product.id
            binding.temp.text = product.data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun submitList(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }
}
