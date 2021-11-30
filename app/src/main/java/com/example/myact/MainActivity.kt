package com.example.myact

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myact.JSON.GET
import com.example.myact.ui.theme.MYACTTheme
import org.intellij.lang.annotations.JdkConstants
import org.json.JSONArray
import javax.xml.transform.URIResolver
import android.graphics.*
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.myact.JSON.DELETE
import com.example.myact.ui.theme.Shapes
import com.example.myact.ui.theme.moy
import dev.chrisbanes.accompanist.glide.GlideImage
import java.io.File
import java.util.*

var arraY :JSONArray? = null
class MainActivity : ComponentActivity() {
    val urik = mutableStateOf<Uri?>(null)
    val select = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        urik.value = uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MYACTTheme {
            Test()
            }
        }
    }
    @Preview
    @Composable
    fun Test(){
        var hi = remember{ mutableStateOf("Hello") }
        var namE = remember {
            mutableListOf<String>()
        }
        var namE1 = remember {
            mutableListOf<String>()
        }
        var namE2 = remember {
            mutableListOf<String>()
        }
        var photo = remember{
            mutableListOf<String>()
        }
        val count = remember {
            mutableStateOf(0)
        }
        var arraYY = remember {
            JSONArray()
        }
        GET.testGet{
                array ->
            hi.value = array.getJSONObject(0).getString("firstName")
            arraYY = array
            count.value = arraYY.length()
        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxSize()) {
            Column(Modifier.fillMaxWidth()
                ) {
                Spacer(modifier = Modifier.size(100.dp))
                LazyColumn(
                    Modifier
                        .scrollable(rememberLazyListState(), Orientation.Vertical)
                        .fillMaxWidth()){
                    for(i in 0 until count.value){
                        if (arraYY!! == null){

                        }else{
                            item {
                                var name = arraYY.getJSONObject(i).getString("firstName")
                                var name1 = arraYY.getJSONObject(i).getString("lastName")
                                var name2 = arraYY.getJSONObject(i).getString("middleName")
                                var id = arraYY.getJSONObject(i).getString("id")
                                val photo = Base64.decode((arraYY.getJSONObject(i).getString("photo")), Base64.NO_WRAP)
                                Chel(id.toInt(),name,name1,name2,photo)
                            }
                        }

                    }

                }
            }
            
        }
            
        

    }

    @Composable
    fun Chel(id:Int,name:String,name1:String,name2:String,image:ByteArray) {
        var bol0 = remember {
            mutableStateOf(true)
        }
        if (bol0.value == true){
            val bol = remember {
                mutableStateOf(false)
            }
            val bol1 = remember {
                mutableStateOf(false)
            }
            val a = BitmapFactory.decodeByteArray(image,0,image.size)
            val b = a.asImageBitmap()

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)) {
                Column(Modifier.fillMaxSize()) {
                    Image(bitmap = b,null, modifier = Modifier
                        .size(150.dp, 200.dp)
                        .align(CenterHorizontally)
                    )
                    Text(text = name,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 10.dp))
                    Text(text = name1,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 10.dp))
                    Text(text = name2,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 10.dp))
                    Row(
                        Modifier
                            .align(CenterHorizontally)
                            .size(height = 70.dp, width = 300.dp)
                    ) {
                        Button(onClick = {bol.value = true},
                            Modifier
                                .padding(top = 20.dp)
                                .size(120.dp)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "DELETE",style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
                        }
                        Button(onClick = {bol1.value = true},
                            Modifier
                                .padding(top = 20.dp, start = 60.dp)
                                .size(120.dp)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(moy)
                        ) {
                            Text(text = "CHANGE",style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
                        }
                    }

                }


            }
            if (bol1.value == true){
                AlertDialog(onDismissRequest = {bol1.value = false}, buttons = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .size(500.dp)){
                        Column(
                            modifier = Modifier.align(TopCenter)
                        ) {

                            if (urik.value != null){
                                val sourse = ImageDecoder.createSource(contentResolver,urik.value!!)
                                val image = ImageDecoder.decodeBitmap(sourse)
                                Image(bitmap = image.asImageBitmap(),null,
                                    modifier = Modifier
                                        .size(180.dp, 240.dp)
                                        .align(CenterHorizontally)
                                        .padding(top = 30.dp))

                            }

                        }
                        Row() {
                            
                        }
                        Button(onClick = { select.launch("image/*") },
                            modifier = Modifier
                                .padding(bottom = 50.dp)
                                .align(BottomCenter)
                                .size(200.dp, 40.dp),
                            colors = ButtonDefaults.buttonColors(Color.Blue))
                        {
                            Text(text = "SELECT", style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
                        }
                    }

                }
                )


            }
            if (bol.value == true){
                AlertDialog(onDismissRequest = {}, title = {Text(text = "Удаление Объекта")}, buttons = { Column {
                    Text(text = "Вы уверены что хотите удалить объект с сервера?",

                        modifier = Modifier
                            .padding(top = 30.dp, bottom = 10.dp)
                            .align(CenterHorizontally)
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, start = 20.dp)) {
                        Button(onClick = {bol.value = false},Modifier.size(120.dp,40.dp), border = BorderStroke(3.dp,Color.Black), colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        )) {
                            Text(text = "НЕТ", style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold))
                        }
                        Button(onClick = {DELETE.del(id,applicationContext)
                            bol.value = false
                                         bol0.value = false },
                            Modifier
                                .size(120.dp, 40.dp)
                                .padding(start = 50.dp), border = BorderStroke(3.dp,Color.Black), colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White
                            )) {
                            Text(text = "Да", style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold))
                        }
                    }

                }
                }
                )


            }
        }


    }


}



