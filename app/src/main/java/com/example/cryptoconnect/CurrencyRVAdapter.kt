package com.example.cryptoconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class CurrencyRVAdapter(private val currencyList : List<CurrencyModel>) : RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder>(){

    private val df2: DecimalFormat = DecimalFormat("#.##")
    private val context: Context? = null

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val symbol : TextView = itemView.findViewById(R.id.idTVSymbol)
        val name : TextView = itemView.findViewById(R.id.idTVName)
        val rate : TextView = itemView.findViewById(R.id.idTVRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.currency_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.name.text = currency.name
        holder.symbol.text = currency.symbol
        holder.rate.text = ("$ " + df2.format(currency.price))
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }
}