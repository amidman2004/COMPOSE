package com.example.myact.JSON

import android.annotation.SuppressLint
import android.icu.util.UniversalTimeScale
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class GET {
    companion object{
        val url = "https://api.cfif31.ru/library/autors"
        val scope = MainScope()
        fun testGet(onResponse:(array:JSONArray)->Unit) {
            scope.launch(Dispatchers.IO){
                (URL(url).openConnection() as HttpURLConnection).run {
                    requestMethod  ="GET"
                    doInput = true
                    connect()
                    val response = inputStream.bufferedReader().readText()
                    val array = JSONArray(response)
                    scope.launch(Dispatchers.Main){
                        onResponse(array)
                    }
                }
            }
        }
    }
}
