package com.example.retrofitdataclasslesson

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.retrofitdataclasslesson.databinding.ActivityMainBinding
import com.example.retrofitdataclasslesson.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        binding.request.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val api = retrofit.create(MainApi::class.java)
                val model = api.getWeatherData(
                    "904bf5f8d50c485a84145922242109",
                    "Yakutsk",
                    "3",
                    "no",
                    "no"
                )
                binding.temp.text = model.current.temp_c.toString()
                binding.date.text = model.location.name
            }
        }
    }
}