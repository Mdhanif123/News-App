package com.example.newsapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val spannableString = SpannableString(resources.getString(R.string.feedBack_Text))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val emailIntent = Intent(Intent.ACTION_SENDTO)
                emailIntent.data = Uri.parse("mailto:bijhanif@gmail.com")
                startActivity(Intent.createChooser(emailIntent, "Send feedback"))
            }
        }
        spannableString.setSpan(clickableSpan,48,66,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        feedBack.movementMethod = LinkMovementMethod.getInstance()
        feedBack.text = spannableString
    }
}
