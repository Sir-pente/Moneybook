package com.example.moneybook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoneyAdapter(private var trnsList: ArrayList<Transaction>) : RecyclerView.Adapter<MoneyAdapter.MoneyViewHolder>() {
    private var onClickDeleteItem: ((Transaction) -> Unit)? = null
    private var onClickModifyItem: ((Transaction) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoneyViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.fragment_item, parent, false)
    )

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {

        val trns = trnsList[position]
        holder.bindView(trns)
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(trns)}
        holder.btnModify.setOnClickListener{ onClickModifyItem?.invoke(trns)}
    }

    fun setOnClickDeleteItem(callback:(Transaction)->Unit){
        this.onClickDeleteItem = callback
    }

    fun setOnClickModifyItem(callback:(Transaction)->Unit){
        this.onClickDeleteItem = callback
    }

    override fun getItemCount(): Int {
        return trnsList.size
    }


    class MoneyViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var Money = view.findViewById<TextView>(R.id.fragMoneyValue)
        private var Id = view.findViewById<TextView>(R.id.fragID)
        private var Reason = view.findViewById<TextView>(R.id.fragReason)
        private var ExpInc = view.findViewById<TextView>(R.id.fragExpInc)
        private var Date = view.findViewById<TextView>(R.id.fragDate)
        var btnDelete = view.findViewById<TextView>(R.id.fragDelete)
        var btnModify = view.findViewById<TextView>(R.id.fragModify)

        fun bindView(trns: Transaction){
            Id.text = trns.id.toString()
            Money.text = trns.moneyVal.toString()
            ExpInc.text = trns.ei.toString()
            Reason.text = trns.reason
            Date.text = trns.date.toString()
        }
    }
}