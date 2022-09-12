package com.example.moneybook

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_modify.*
import java.time.format.DateTimeFormatter

private  var id:Int = 0

class modifyActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        id = intent.getSerializableExtra("id") as Int
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        switchIEmod.setOnCheckedChangeListener { _, isChecked ->
            if (switchIEmod.isChecked) {
                textViewIEmod.setText("+")

            } else {
                textViewIEmod.setText("-")

            }
        }

        btnModifymod.setOnClickListener{
            val ei: String
            val db = DBHelper(this, null)

            if (switchIEmod.isChecked) {
                ei = "income"
            } else {
                ei = "expense"
            }

            var datef = editTextDatemod.text.toString()
            var date = datef.format(formatter)

            var value = editTextMoneyValuemod.text.toString().toFloat()
            var reason = editTextreasonmod.text.toString()

            db.modifyTransaction(id, ei, value, reason, date.toString())

            Toast.makeText(this, id.toString() + " modified", Toast.LENGTH_LONG).show()
            editTextMoneyValuemod.text.clear()
            editTextreasonmod.text.clear()
            editTextDatemod.text.clear()
        }
    }
}