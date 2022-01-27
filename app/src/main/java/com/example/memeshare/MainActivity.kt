package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var imageurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadMeme()
    }
  private fun LoadMeme(){

      val Loadbar=findViewById<ProgressBar>(R.id.Loading_bar)
      Loadbar.visibility=View.VISIBLE

      val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
      val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
          { response ->
              // Display the first 500 characters of the response string
              imageurl= response.getString("url")
              val Memeimage = findViewById<ImageView>(R.id.Meme_image)
              Glide.with(this).load(imageurl).listener(object: RequestListener<Drawable>{
                  override fun onLoadFailed(
                      e: GlideException?,
                      model: Any?,
                      target: Target<Drawable>?,
                      isFirstResource: Boolean
                  ): Boolean {
                      Loadbar.visibility=View.GONE
                      return false
                  }

                  override fun onResourceReady(
                      resource: Drawable?,
                      model: Any?,
                      target: Target<Drawable>?,
                      dataSource: DataSource?,
                      isFirstResource: Boolean
                  ): Boolean {
                      Loadbar.visibility=View.GONE
                      return false
                  }

              }).into(Memeimage)

          },
          {
              Toast.makeText(this, "Something Not Working Properly",Toast.LENGTH_LONG).show()
          })

// Add the request to the RequestQueue.
      MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

  }
    fun NextMeme(view: android.view.View) {
       LoadMeme()
    }
    fun ShareMeme(view: android.view.View) {
     val intent=Intent(Intent.ACTION_SEND)
        intent.type="Text/Plain"
        intent.putExtra(Intent.EXTRA_TEXT,"$imageurl")
        val chooser=Intent.createChooser(intent,"Share")
       startActivity(chooser)
    }
}

