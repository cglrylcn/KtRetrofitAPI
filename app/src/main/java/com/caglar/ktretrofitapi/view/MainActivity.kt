package com.caglar.ktretrofitapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caglar.ktretrofitapi.adapter.RecyclerViewAdapter
import com.caglar.ktretrofitapi.databinding.ActivityMainBinding
import com.caglar.ktretrofitapi.model.CryptoModel
import com.caglar.ktretrofitapi.service.CryptoAPI
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel> ?= null
    private var recyclerViewAdapter: RecyclerViewAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()
        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { lCM ->
                        recyclerViewAdapter = RecyclerViewAdapter(lCM as ArrayList<CryptoModel>, this@MainActivity)
                        binding.recyclerView.adapter = recyclerViewAdapter

                        cryptoModels = ArrayList(lCM)
                        for (crM in cryptoModels!!) {
                            println(crM.currency)
                            println(crM.price)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Coin: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}