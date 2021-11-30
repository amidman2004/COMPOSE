package com.example.myact.JSON

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class POST {
    companion object {
        val url = "https://api.cfif31.ru/Library/Autors"
        val scope = MainScope()
        fun put(id:Int,name:String,name1:String,name2:String,photo:String){
            scope.launch(Dispatchers.IO){
                val user = JSONObject()
                user.put("id",id)
                user.put("firstName",name)
                user.put("lastName",name1)
                user.put("middleName",name2)
                user.put("photo",photo)
                (URL(url+"/$id").openConnection() as HttpURLConnection).run{
                    requestMethod = "PUT"
                }
            }
        }
    }
}