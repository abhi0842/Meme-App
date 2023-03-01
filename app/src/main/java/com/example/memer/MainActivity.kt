package com.example.memer

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
   private fun loadmeme(){
       val process=findViewById<ProgressBar>(R.id.progressbar)
       process.visibility=View.VISIBLE
       // Instantiate the RequestQueue.
       val queue = Volley.newRequestQueue(this)
       val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
       val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
           Response.Listener { response ->
               currentImageurl=response.getString("url")
               Glide.with(this).load(currentImageurl).listener(object:RequestListener<Drawable>{
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                      process.visibility=View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       process.visibility=View.GONE
                       return false
                   }
               }).into(findViewById(R.id.imageView))
           },
           Response.ErrorListener { error ->

           }
       )

// Add the request to the RequestQueue.
       queue.add(jsonObjectRequest)
   }
    fun sharememe(view: View) {
        val intent =Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this cool meme from the reddit api $currentImageurl")
     val chooser=Intent.createChooser(intent,"Share this meme using...... ")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadmeme()
    }
}