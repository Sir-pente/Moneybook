package com.example.moneybook

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.android.synthetic.main.activity_statistics.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

private  var id:Int = 0

class modifyActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        id = intent.getSerializableExtra("id") as Int
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        switchIEmod.setOnCheckedChangeListener { _, isChecked ->
            if (switchIEmod.isChecked) {
                textViewIEmod.setText("+")

            } else {
                textViewIEmod.setText("-")

            }
        }

        btnModifymod.setOnClickListener{
          if (editTextMoneyValuemod.text.isNotEmpty()  &&
                  editTextreasonmod.text.isNotEmpty() ) {
              val ei: String
              val db = DBHelper(this, null)

              if (switchIEmod.isChecked) {
                  ei = "income"
              } else {
                  ei = "expense"
              }

              var value = editTextMoneyValuemod.text.toString().toFloat()
              val df = DecimalFormat("#.##")
              df.roundingMode = RoundingMode.HALF_EVEN
              var valueform = df.format(value).toFloat()

              var reason = editTextreasonmod.text.toString()

              if (switchDate.isChecked && editTextDatemod.text.isNotEmpty()) {
                  if (editTextDatemod.text.toString().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$".toRegex())) {

                      var datef = editTextDatemod.text.toString()
                      var date = datef.format(formatter)
                      db.modifyTransactionDate(id, ei, valueform, reason, date.toString())
                      Toast.makeText(this, id.toString() + " modified", Toast.LENGTH_LONG).show()
                      editTextMoneyValuemod.text.clear()
                      editTextreasonmod.text.clear()
                      editTextDatemod.text.clear()
                  } else {
                      Toast.makeText(this,  "wrong date format", Toast.LENGTH_LONG).show()
                      editTextDatemod.text.clear()
                  }
              } else {
                  db.modifyTransaction(id, ei, valueform, reason)
                  Toast.makeText(this, id.toString() + " modified", Toast.LENGTH_LONG).show()
                  editTextMoneyValuemod.text.clear()
                  editTextreasonmod.text.clear()
                  editTextDatemod.text.clear()
              }
          }
            else {
              Toast.makeText(this,  " need to complete fields", Toast.LENGTH_LONG).show()
          }
        }
    }
}