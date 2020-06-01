package com.example.newsapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.dataModel.ArticleData
import com.example.newsapp.helpers.ConstantStringHelper
import com.example.newsapp.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_news_details.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ShowNewsDetails : AppCompatActivity() {
    private lateinit var article: ArticleData.Article
    @RequiresApi(Build.VERSION_CODES.O)
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_news_details)
        article = intent.getSerializableExtra("NewsData") as ArticleData.Article
        Log.d("Received data", "$article")
        setUp()

        txtReceivedURL.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(article.url)
            startActivity(openURL)
        }

        btnShare.setOnClickListener {
            if (!article.url.isNullOrEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_TEXT,article.url)
                startActivity(Intent.createChooser(shareIntent,"Share Using"))
            } else {
                Toast.makeText(this,"Unable to share this article", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun setUp() {

        MobileAds.initialize(this,getString(R.string.appAdId))
        val adRequest = AdRequest.Builder().build()
        adViewNewsDetails.loadAd(adRequest)
        adViewNewsDetails.visibility = View.GONE
        adViewNewsDetails.adListener = object : AdListener() {
            override fun onAdLoaded() {
                adViewNewsDetails.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }

        if (!article.title.isNullOrEmpty()) txtTitle.text = article.title else txtTitle.text = " "
        if (!article.publishedAt.isNullOrEmpty()) txtPublishDetails.text =
            OffsetDateTime.parse(article.publishedAt).format(dateFormat).toString()
        if (!article.author.isNullOrEmpty()) txtAuthorName.text =
            article.author else txtAuthorName.text = ConstantStringHelper.unKnownAuthor
        if (!article.content.isNullOrEmpty()) txtReceivedContent.text =
            article.content else txtReceivedContent.text = " "
        if (!article.description.isNullOrEmpty()) txtReceivedDescription.text =
            article.description else txtReceivedDescription.text = " "
        if (!article.url.isNullOrEmpty()) txtReceivedURL.text =
            article.url else txtReceivedURL.text = " "
        if (!article.urlToImage.isNullOrEmpty()) Picasso.get().load(article.urlToImage).centerCrop().fit().into(
            newsImage
        )
    }
}
