package com.caglar.ktretrofitapi.adapter

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caglar.ktretrofitapi.R
import com.caglar.ktretrofitapi.databinding.RowLayoutBinding
import com.caglar.ktretrofitapi.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    private val colors: Array<String> = arrayOf("#000000","#3D0000","#FF0000","#3E2C41","#1C0C5B","#E2703A","#52057B")
    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener) {
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            binding.linearLayout.setBackgroundColor(Color.parseColor(colors[position % 7]))
            binding.coinName.text = cryptoModel.currency
            binding.coinPrice.text = cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        val view = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}