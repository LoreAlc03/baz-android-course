package com.example.cryptocurrencyapp.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyapp.databinding.CryptoItemBinding
import com.example.cryptocurrencyapp.domain.entity.CryptoBookDTO
import com.example.cryptocurrencyapp.utils.Utils

class CryptoAdapter(private val click: (CryptoBookDTO) -> Unit) :
    ListAdapter<CryptoBookDTO, CryptoAdapter.ViewHolder>(CoinDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CryptoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = getItem(position)
        holder.bind(coin)
        holder.itemView.setOnClickListener {
            click.invoke(coin)
        }
    }

    inner class ViewHolder(private val binding: CryptoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: CryptoBookDTO) {
            with(binding) {
                val name = Utils.cleanString(coin.book)
                imgCrypto.setImageResource(coin.logo)
                txtMx.text = name
                txtCryptoName.text = coin.name
                txtMinPrice.text = coin.minPrice
                txtMaxPrice.text = coin.maxPrice
            }
        }
    }

    private object CoinDiffCallback : DiffUtil.ItemCallback<CryptoBookDTO>() {
        override fun areItemsTheSame(oldItem: CryptoBookDTO, newItem: CryptoBookDTO): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CryptoBookDTO,
            newItem: CryptoBookDTO
        ): Boolean =
            oldItem == newItem
    }
}
