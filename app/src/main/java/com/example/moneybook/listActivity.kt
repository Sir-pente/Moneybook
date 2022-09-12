package com.example.moneybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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

        adapter?.setOnClickDeleteItem {
            deleteTransaction(it.id)
        }

        adapter?.setOnClickModifyItem {
            modifyTransaction(it.id)
        }

    }

    private fun modifyTransaction(id: Int){
        val intent = Intent(this, modifyActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun deleteTransaction(id: Int) {
        val db = DBHelper (this , null)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete item ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _->
            db.deleteTransaction(id)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

}
