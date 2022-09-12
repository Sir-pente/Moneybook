package com.example.moneybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var adapter: MoneyAdapter
private lateinit var recyclerView: RecyclerView
private lateinit var trnsList : ArrayList<Transaction>


class listActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        trnsList = intent.getSerializableExtra("list") as ArrayList<Transaction>
        val layoutManager = LinearLayoutManager(this)
        recyclerView = this.findViewById(R.id.list)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MoneyAdapter(trnsList)
        recyclerView.adapter = adapter

    }

}
