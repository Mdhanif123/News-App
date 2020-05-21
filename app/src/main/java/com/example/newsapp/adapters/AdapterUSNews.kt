package com.example.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.dataModel.ArticleData
import com.example.newsapp.helpers.ConstantStringHelper
import com.example.newsapp.R
import com.example.newsapp.activities.ShowNewsDetails
import com.squareup.picasso.Picasso
import java.io.Serializable


class AdapterUSNews(var news: ArticleData) : RecyclerView.Adapter<AdapterUSNews.USNewsAdapter>() {

    var context: Context? = null

    class USNewsAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var txtTitle: TextView =
            itemView.findViewById<View>(R.id.article_title) as TextView
        internal var txtAuthor: TextView =
            itemView.findViewById<View>(R.id.article_author) as TextView
        internal var imgNewsPhoto: ImageView =
            itemView.findViewById<View>(R.id.article_urlToImage) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): USNewsAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        context = parent.context
        return USNewsAdapter(view)
    }

    override fun getItemCount(): Int {
        return news.articles.count()
    }

    override fun onBindViewHolder(holder: USNewsAdapter, position: Int) {
        holder.txtTitle.text = news.articles[position].title
        if (news.articles[position].author.isNullOrEmpty()) {
            holder.txtAuthor.text = ConstantStringHelper.unKnownAuthor
        } else {
            holder.txtAuthor.text = news.articles[position].author
        }
        if (!(news.articles[position].urlToImage.isNullOrEmpty())) {
            Picasso.get()
                .load(news.articles[position].urlToImage)
                .centerCrop()
                .fit()
                .into(holder.imgNewsPhoto)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context,
                ShowNewsDetails::class.java)
            intent.putExtra("NewsData", news.articles[position] as Serializable)
            context!!.startActivity(intent)

        }

    }
}