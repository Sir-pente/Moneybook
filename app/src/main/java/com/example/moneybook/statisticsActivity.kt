package com.example.moneybook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_statistics.*
import java.math.RoundingMode
import java.text.DecimalFormat

class statisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        var trnsList: ArrayList<Transaction> = ArrayList()
        val db = DBHelper(this, null)

        buttonCompute.setOnClickListener {
            var inc : Float  =  0.0f
            var exp : Float =  0.0f
            var bal : Float =  0.0f
            if (editTextDate.text.isNotEmpty() && editTextDate2.text.isNotEmpty() && editTextReasonstats.text.isEmpty()) {

                if (editTextDate.text.toString().matches("\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$".toRegex())
                    && editTextDate2.text.toString().matches("\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$".toRegex())){

                var datestart = "'"+editTextDate.text.toString()+"'"
                var datefin = "'"+editTextDate2.text.toString()+"'"

                trnsList = db.getTransBetweenDate(datestart, datefin)
                } else { Toast.makeText(this,  "wrong date format", Toast.LENGTH_LONG).show()}

            } else if (editTextReasonstats.text.toString().isNotEmpty() && editTextDate.text.isEmpty() && editTextDate2.text.isEmpty()){

                var reason = editTextReasonstats.text.toString()
                trnsList = db.getTransByReason(reason)

            } else if (editTextDate.text.isNotEmpty() && editTextDate2.text.isNotEmpty() &&
                        editTextReasonstats.text.toString().isNotEmpty()) {

                if (editTextDate.text.toString().matches("\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$".toRegex())
                    && editTextDate2.text.toString().matches("\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\$".toRegex())) {

                    var datestart = "'" + editTextDate.text.toString() + "'"
                    var datefin = "'" + editTextDate2.text.toString() + "'"
                    var reason = editTextReasonstats.text.toString()
                    trnsList = db.getTransByDateAndReas(datestart, datefin, reason)

                } else { Toast.makeText(this,  "wrong date format", Toast.LENGTH_LONG).show()}
            }

            var trans : Transaction
            for (trans in trnsList) {
                if (trans.ei.toString().equals("income")) {

                    var money = trans.moneyVal.toFloat()

                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.HALF_EVEN
                    var moneyform = df.format(money).toFloat()

                    inc = inc + moneyform

                } else  {
                    var money = trans.moneyVal.toFloat()

                    val df = DecimalFormat("#.##")
                    df.roundingMode = RoundingMode.HALF_EVEN
                    var moneyform = df.format(money).toFloat()
                    exp = exp + moneyform
                }
            }

            bal = inc - exp

            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_EVEN
            var valueform = df.format(bal).toFloat()

            textViewInc.setText(inc.toString())
            textViewExp.setText(exp.toString())
            textViewBal.setText((valueform.toString()))
        }
    }
}