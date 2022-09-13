package com.example.moneybook

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.moneybook.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import java.lang.NumberFormatException
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        var date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd ")


        switchIE.setOnCheckedChangeListener { _, isChecked ->
            if (switchIE.isChecked) {
                textViewIE.setText("+")

            } else {
                textViewIE.setText("-")

            }
        }

        buttonNewItem.setOnClickListener {

            if (editTextMoneyValue.text.isNotEmpty() && editTextReason.text.isNotEmpty()) {

                val db = DBHelper(this, null)
                val value = editTextMoneyValue.text.toString().toFloat()
                val df = DecimalFormat("#.##")

                df.roundingMode = RoundingMode.HALF_EVEN
                var valueform = df.format(value).toFloat()
                var ei: String
                var reason = editTextReason.text.toString()

                if (switchIE.isChecked) {
                    ei = "income"
                } else {
                    ei = "expense"
                }

                val datepat = formatter.format(date)

                db.addItem(valueform, ei, reason, datepat)

                Toast.makeText(this, value.toString() + " added to database", Toast.LENGTH_LONG)
                    .show()

                editTextMoneyValue.text.clear()
                editTextReason.text.clear()
            } else {
                Toast.makeText(this, "need to complete fields", Toast.LENGTH_LONG)
                    .show()
            }

        }

        buttonStats.setOnClickListener {
            val intent = Intent(this, statisticsActivity::class.java)
            startActivity(intent)
        }

        buttonList.setOnClickListener {
            val db = DBHelper(this, null)

            var trnsList: ArrayList<Transaction>
            trnsList = db.getTransactions()

            val intent = Intent(this, listActivity::class.java)
            intent.putExtra("list", trnsList)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}