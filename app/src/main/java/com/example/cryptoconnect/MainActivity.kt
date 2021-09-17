package com.example.cryptoconnect

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Filter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject




class MainActivity : AppCompatActivity() {

    lateinit var editText : EditText
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var currencyList : List<CurrencyModel>
    lateinit var currencyAdapter : CurrencyRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.idEdtCurrency)
        recyclerView = findViewById(R.id.idRvCurrency)
        progressBar = findViewById(R.id.progressBar)
        currencyList = ArrayList<CurrencyModel>()

        getCurrencyData()

    }

    private fun getCurrencyData() {
        val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest =object :  JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {response->
                Log.e("TAG", "SUCCESS RESPONSE IS $response")
                progressBar.visibility = View.GONE
                try {
                    val currencyArray = response.getJSONArray("data")
                    if (currencyArray.length() == 0) {
                        Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show()
                    }
                    for (i in 0 until currencyArray.length()) {
                        val dataObj: JSONObject = currencyArray.getJSONObject(i)
                        val symbol = dataObj.getString("symbol")
                        val name = dataObj.getString("name")
                        val quote = dataObj.getJSONObject("quote")
                        val USD = quote.getJSONObject("USD")
                        val price = USD.getDouble("price")

                        val currency = CurrencyModel(name, symbol, price)
                        currencyList = currencyList + currency
                    }
                    currencyAdapter = CurrencyRVAdapter(currencyList)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = currencyAdapter
                    currencyAdapter.notifyDataSetChanged()
                } catch (e: JSONException){
                    progressBar.visibility = View.GONE
                    e.printStackTrace()
                }


        },
            Response.ErrorListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this,"Failed to get the data",Toast.LENGTH_SHORT).show()
            })
        {
            override fun getHeaders() : Map<String,String> {
                val headers: HashMap<String, String> = HashMap()
                headers["X-CMC_PRO_API_KEY"] = "Your_Api_Key"
                // at last returning headers
                // at last returning headers
                return headers
            }
        }

    queue.add(jsonObjectRequest)
    }


}
