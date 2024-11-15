package com.example.bookshelfapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var noInternetDialog: AlertDialog
    private val handler = Handler()
    private val runnable = Runnable { checkInternetConnection() }
    private val viewModel: BookViewModel by viewModels()
    private lateinit var bookAdapter: BookAdapter
    private lateinit var recyclerViewBooks: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkInternetConnection()

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
        recyclerViewBooks.layoutManager = GridLayoutManager(this, 2)

        viewModel.booksLiveData.observe(this, Observer { books ->
            bookAdapter = BookAdapter(books)
            recyclerViewBooks.adapter = bookAdapter
        })
        viewModel.fetchBooks("Re zero", "AIzaSyC8fpaiBCm0n52AF_Cwlk3CvDlhne_xjOk")

        val refresh_button = findViewById<Button>(R.id.refresh_button)
        refresh_button.setOnClickListener {
            checkInternetConnection()
            viewModel.fetchBooks("Re zero", "AIzaSyC8fpaiBCm0n52AF_Cwlk3CvDlhne_xjOk")
        }

    }
    private fun checkInternetConnection() {
        if (!NetworkUtils.isInternetAvailable(this)) {
            showNoInternetDialog()
        } else {
            if (::noInternetDialog.isInitialized && noInternetDialog.isShowing) {
                noInternetDialog.dismiss()
            }
            handler.removeCallbacks(runnable)
        }
    }

    private fun showNoInternetDialog() {
        if (!::noInternetDialog.isInitialized || !noInternetDialog.isShowing) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Internet Connection")
            builder.setMessage("This app requires internet access. Please enable it in your device settings.")
            builder.setPositiveButton("Settings") { _, _ ->
                val intent = Intent(android.provider.Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            }
            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            noInternetDialog = builder.create()
            noInternetDialog.setCancelable(false)
            noInternetDialog.show()
        }
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}