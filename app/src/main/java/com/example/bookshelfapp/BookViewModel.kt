package com.example.bookshelfapp

import android.util.Log
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BookViewModel : ViewModel() {
    private val _booksLiveData = MutableLiveData<List<BookItem>>()
    val booksLiveData: LiveData<List<BookItem>> = _booksLiveData

    fun fetchBooks(query: String, apiKey: String) {
        val call = RetrofitInstance.api.getBooks(query = query, apiKey = apiKey)

        call.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items ?: emptyList()
                    _booksLiveData.value = books
                } else {
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
            }
        })
    }
}

