package com.example.myact.JSON

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class DELETE {
    companion object{
        val url = "https://api.cfif31.ru/Library/Autors"
        val scope = MainScope()
        fun del(id:Int,context:Context){
            scope.launch(Dispatchers.IO){
                (URL(url+"/$id").openConnection() as HttpURLConnection).run {
                    requestMethod = "DELETE"
                    val code = responseCode
                    val message = responseMessage
                    scope.launch(Dispatchers.Main){
                        Toast.makeText(context, "$code $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}