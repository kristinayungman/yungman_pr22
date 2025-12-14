package com.dignerdranch.anroid.pract22

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var cityInput:EditText
    private lateinit var btn:Button
    private lateinit var text:TextView
    private lateinit var coordinator: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cityInput=findViewById(R.id.city1)
        btn=findViewById(R.id.button)
        text=findViewById(R.id.result)
        coordinator=findViewById(android.R.id.content)
        btn.setOnClickListener { getResult(cityInput.text.toString()) }
    }fun getResult(city: String){
        if(city.isNotEmpty() && city != null){
            var key="309444ae447b73b17e2a35d523d8d83c"
            var url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                {
                        response->
                    val obj = JSONObject(response)
                    val main = obj.getJSONObject("main")
                    val temp = main.getString("temp")
                    val humidity = main.getString("humidity")
                    val cityru = obj.getString("name")
                    val info = """
                        Погода в городе: $cityru
                        
                        Температура: ${temp}°C
                        
                        Влажность: ${humidity}%
                    """.trimIndent()
                    text.text=info.toString()
                    Log.d("MyLog", "Response: ${main.getString("temp")}")
                },
                {
                    text.text="Введеный вами город не найден"
                    Log.d("MyLog", "Volley error: $it")
                }
            )
            queue.add(stringRequest)
        }
        else{
            val sn = Snackbar.make(coordinator, "Введите название города",Snackbar.LENGTH_LONG)
            sn.setTextColor(Color.RED)
            sn.show()
        }
    }
}
