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
import kotlinx.android.synthetic.main.fragment_first.*
import java.lang.NumberFormatException
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

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        var date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy ")


        switchIE.setOnCheckedChangeListener { _, isChecked ->
            if (switchIE.isChecked) {
                textViewIE.setText("+")

            } else {
                textViewIE.setText("-")

            }
        }

        buttonNewItem.setOnClickListener {

            val db = DBHelper(this, null)
            var value : Float = 0.0f
            var reason : String = ""
            var ei : String
        try {
             value = editTextMoneyValue.text.toString().toFloat()
             reason = editTextReason.text.toString()
        }
        catch (e:NumberFormatException) {e.printStackTrace()}

        if (switchIE.isChecked) {
                ei = "income"
            } else {
                ei = "expense"
            }

            val datepat = formatter.format(date)

            db.addItem(value, ei ,reason, datepat)

            Toast.makeText(this, value.toString() + " added to database", Toast.LENGTH_LONG).show()

            editTextMoneyValue.text.clear()
            editTextReason.text.clear()
        }

        buttonList.setOnClickListener {
            val db = DBHelper(this, null)

            var trnsList: ArrayList<Transaction>
            trnsList = db.getTransactions()

            val intent = Intent(this, listActivity::class.java)
            intent.putExtra("list", trnsList)
            startActivity(intent)
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}