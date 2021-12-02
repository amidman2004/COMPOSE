package com.example.myact.JSON

import android.content.Context
import android.widget.Toast
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
        fun put(context:Context,id:Int,name:String,name1:String,name2:String,
                description:String,birthDate:String,deadDate:String,photo:String){
            scope.launch(Dispatchers.IO){
                val user = JSONObject()
                user.put("id",id)
                user.put("firstName",name)
                user.put("lastName",name1)
                user.put("middleName",name2)
                user.put("description",description)
                user.put("birthDate",birthDate)
                user.put("deadDate",deadDate)
                if (photo!="null"){
                    user.put("photo",photo)
                }
                (URL(url+"/$id").openConnection() as HttpURLConnection).run{
                    requestMethod = "PUT"
                    doOutput = true
                    addRequestProperty("Content-Type","application/json")
                    val writer = outputStream.bufferedWriter()
                    writer.write(user.toString())
                    writer.flush()
                    outputStream.flush()
                    connect()
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