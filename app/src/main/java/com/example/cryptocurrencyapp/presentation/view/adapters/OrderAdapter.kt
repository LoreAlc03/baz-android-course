package com.example.cryptocurrencyapp.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyapp.databinding.DetailItemBinding
import com.example.cryptocurrencyapp.domain.entity.OrderBookDTO

class OrderAdapter : ListAdapter<OrderBookDTO, OrderAdapter.ViewHolder>(DetailDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailCoin = getItem(position)
        holder.bind(detailCoin)
    }

    inner class ViewHolder(private val binding: DetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: OrderBookDTO) {
            with(binding) {
                txtCryptoValue.text = coin.book
                txtValueAmount.text = coin.amount
                txtValuePrice.text = coin.price
                txtValueType.text = coin.type
            }
        }
    }
}

private object DetailDiffCallback : DiffUtil.ItemCallback<OrderBookDTO>() {
    override fun areItemsTheSame(oldItem: OrderBookDTO, newItem: OrderBookDTO): Boolean =
        oldItem.book == newItem.book

    override fun areContentsTheSame(oldItem: OrderBookDTO, newItem: OrderBookDTO): Boolean =
        oldItem == newItem
}
