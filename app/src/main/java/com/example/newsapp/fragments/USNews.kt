package com.example.newsapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapters.AdapterUSNews
import com.example.newsapp.helpers.ApiHelper
import com.example.newsapp.dataModel.ArticleData
import com.example.newsapp.R
import com.example.newsapp.helpers.NewsApiInterface
import kotlinx.android.synthetic.main.usnews.*
import kotlinx.android.synthetic.main.usnews.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class USNews : Fragment() {

        lateinit var adapter: AdapterUSNews

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.usnews, container, false)
        view.usNewsProgressBar.visibility = View.VISIBLE
        getData()
        return view
    }

    private fun getData() {
        val retrofit = Retrofit.Builder().baseUrl(ApiHelper.baseUrl).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service = retrofit.create(NewsApiInterface::class.java)
        val call = service.getCountryNews(
            ApiHelper.countryUS,
            ApiHelper.apiKey)
        call.enqueue(object: Callback<ArticleData> {
            override fun onFailure(call: Call<ArticleData>, t: Throwable) {
                usNewsProgressBar.visibility = View.GONE
                Log.d("Failure", t.toString())
            }
            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                usNewsProgressBar.visibility = View.GONE
                val responseReceived = response.body()
                recyclerViewForUSNews.layoutManager = LinearLayoutManager(this@USNews.context)
                adapter = AdapterUSNews(responseReceived!!)
                recyclerViewForUSNews.adapter = adapter
            }
        })
    }

}
