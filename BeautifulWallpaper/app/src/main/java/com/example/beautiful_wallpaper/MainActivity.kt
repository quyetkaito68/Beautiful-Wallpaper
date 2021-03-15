package com.example.beautiful_wallpaper

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.beautiful_wallpaper.api.RetrofitInstance
import com.example.beautiful_wallpaper.model.MyData
import com.example.beautiful_wallpaper.repository.Repository
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var textView: TextView
    private lateinit var imageFlickr: ImageView
    private lateinit var imageDevice: ImageView
    private lateinit var buttonBrowser: Button
    private lateinit var links: ArrayList<String>
    private var selectedUriList: List<Uri>? = null


    //https://www.flickr.com/services/rest/?method=flickr.photos.getPopular&
    // api_key=d0b35bb8f5b40cf47de1b2fef586c1a4&user_id=192231193%40N06&format=json&nojsoncallback=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        if (isNetworkAvailable(this)==true){
            callAPIFirstWay()
        }

        //callAPI()

        buttonBrowser.setOnClickListener {
            requestPermission()
        }

    }

    private fun callAPIFirstWay() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getData()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val myData: MyData? = response.body()

                if (myData != null) {
                    links = myData.getLinks() as ArrayList<String>
                    textView.text = links[1]
                    Glide.with(this@MainActivity).load(textView.text).into(imageFlickr)
                }
            } else {
                Log.d("Response", response.errorBody().toString())
                textView.text = response.code().toString()
            }

        })
    }

    private fun requestPermission() {
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT).show()
                openImagePiker()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .check();
    }

    private fun openImagePiker() {
        TedBottomPicker.with(this@MainActivity)
            .show {
                Glide.with(this).load(it).into(imageDevice)
            }
    }


    private fun initComponents() {
        textView = findViewById(R.id.tv_hello)
        imageFlickr = findViewById(R.id.img_data)
        imageDevice = findViewById(R.id.img_from_device)
        buttonBrowser = findViewById(R.id.btn_browser)
    }

    private fun callAPI() {
        RetrofitInstance.api.getData2(
            "flickr.photos.getPopular",
            "d0b35bb8f5b40cf47de1b2fef586c1a4",
            "192231193@N06", "json", 1
        )
            .enqueue(object : Callback<MyData?> {
                override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                    val myData: MyData? = response.body()

                    if (myData != null) {
                        links = myData.getLinks() as ArrayList<String>
                        textView.text = links[0]
                        Glide.with(this@MainActivity).load(textView.text).into(imageFlickr)
                    }
                }

                override fun onFailure(call: Call<MyData?>, t: Throwable) {
                    textView.text = "lỗi"
                }
            })
    }
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}