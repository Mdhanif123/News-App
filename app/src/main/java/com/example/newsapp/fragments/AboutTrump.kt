package com.example.newsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.AdapterNews
import com.example.newsapp.helpers.ApiHelper
import com.example.newsapp.dataModel.ArticleData
import com.example.newsapp.R
import com.example.newsapp.helpers.NewsApiInterface
import kotlinx.android.synthetic.main.about_trump.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AboutTrump : Fragment() {

    lateinit var adapter: AdapterNews

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.about_trump, container, false)
        getData()
        return view
    }

    private fun getData() {
        val retrofit = Retrofit.Builder().baseUrl(ApiHelper.baseUrl).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(NewsApiInterface::class.java)
        val call = service.getAboutTrump(ApiHelper.aboutTrump, ApiHelper.apiKey)
        call.enqueue(object: Callback<ArticleData> {
            override fun onFailure(call: Call<ArticleData>, t: Throwable) {
                aboutTrumpNewsProgressBar.visibility = View.GONE
                Log.d("Failure", t.toString())
            }
            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                aboutTrumpNewsProgressBar.visibility = View.GONE
                val responseReceived = response.body()
                recyclerViewForAboutTrumpNews.layoutManager = LinearLayoutManager(this@AboutTrump.context)
                adapter = AdapterNews(responseReceived!!)
                recyclerViewForAboutTrumpNews.adapter = adapter
            }
        })
    }

}
