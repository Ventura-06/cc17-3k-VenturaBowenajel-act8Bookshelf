package com.example.bookshelfapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import android.widget.ImageView

import com.bumptech.glide.Glide

class BookAdapter(private val books: List<BookItem>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_image, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.bookThumbnail)

        fun bind(book: BookItem) {
            val thumbnailUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")

            Glide.with(itemView.context)
                .load(thumbnailUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnailImageView)
        }
    }
}


