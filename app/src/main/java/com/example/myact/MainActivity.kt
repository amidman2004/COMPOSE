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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
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
    var uri:Uri? = null
    val take = registerForActivityResult(ActivityResultContracts.TakePicture()){
        if (it){
            urik.value = uri
        }
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
        var boll = remember {
            mutableStateOf(true)
        }
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxSize()) {
            Column(Modifier.fillMaxWidth()
                ) {
                LazyColumn(
                    Modifier
                        .scrollable(rememberLazyListState(), Orientation.Vertical)
                        .fillMaxWidth()){
                    if (boll.value == true){
                        item {
                            Spacer(modifier = Modifier.size(400.dp))
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .align(CenterHorizontally)){
                                Row(modifier = Modifier.align(Center)) {
                                    CircularProgressIndicator(modifier = Modifier.size(50.dp), color = Color.Blue)
                                    Spacer(modifier = Modifier.size(20.dp))
                                    Text(text = "Loading...", fontSize = 40.sp)
                                }
                            }
                        }
                    }

                    for(i in 0 until count.value){
                        if (arraYY!! == null){

                        }else{
                            item {
                                boll.value = false
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
                var ed1 = remember {
                    mutableStateOf("")
                }
                var ed2 = remember {
                    mutableStateOf("")
                }
                var ed3 = remember {
                    mutableStateOf("")
                }
                var ed4 = remember {
                    mutableStateOf("")
                }
                AlertDialog(onDismissRequest = {bol1.value = false
                                               urik.value = null}, buttons = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            ){
                        Column(
                            modifier = Modifier.align(TopCenter)
                        ) {

                            if (urik.value != null){
                                val sourse = ImageDecoder.createSource(contentResolver,urik.value!!)
                                val image = ImageDecoder.decodeBitmap(sourse)
                                Image(bitmap = image.asImageBitmap(),null,
                                    modifier = Modifier
                                        .size(240.dp, 240.dp)
                                        .align(CenterHorizontally)
                                        .padding(top = 30.dp))


                            }else{
                                Spacer(modifier = Modifier.size(20.dp))
                                Box(modifier = Modifier
                                    .height(240.dp)
                                    .width(240.dp)
                                    .fillMaxWidth()
                                    .align(CenterHorizontally)
                                    .background(Color.LightGray)
                                    ){
                                    Row(modifier = Modifier
                                        .align(Center)) {
                                        Icon(painter = painterResource(id = R.drawable.ic_baseline_image_24),
                                            null,
                                        modifier = Modifier.size(70.dp,70.dp))
                                        Text(text = "Your Image", modifier = Modifier
                                            .height(50.dp)
                                            .padding(start = 10.dp)
                                            .align(CenterVertically)
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.size(10.dp))
                            OutlinedTextField(value = ed1.value, label = { Text(text = "Surname")}, onValueChange = {
                                ed1.value = it
                            },
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp)
                                    .align(CenterHorizontally),
                            textStyle = TextStyle(color = Color.Black),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    cursorColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Blue

                                )
                            )
                            Spacer(modifier = Modifier.size(100.dp,10.dp))
                            OutlinedTextField(value = ed2.value, label = { Text(text = "Name")}, onValueChange = {
                                ed2.value = it
                            },
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp)
                                    .align(CenterHorizontally),
                                textStyle = TextStyle(color = Color.Black),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    cursorColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Blue

                                ))
                            Spacer(modifier = Modifier.size(100.dp,10.dp))
                            OutlinedTextField(value = ed3.value, label = { Text(text = "2nd Name")}, onValueChange = {
                                ed3.value = it
                            },
                                modifier = Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp)
                                    .align(CenterHorizontally),
                                textStyle = TextStyle(color = Color.Black),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    cursorColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Blue

                                ))
                            Spacer(modifier = Modifier.size(100.dp,10.dp))
                            OutlinedTextField(value = ed4.value, label = { Text(text = "Description")}, onValueChange = {
                                ed4.value = it

                            },
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp)
                                    .align(CenterHorizontally),
                                textStyle = TextStyle(color = Color.Black),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    cursorColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedBorderColor = Color.Blue

                                )
                                    )
                            Spacer(modifier = Modifier.size(20.dp,30.dp))
                            Row(
                                    Modifier
                                        .align(CenterHorizontally)) {
                                androidx.compose.material.Button(
                                    onClick = { select.launch("image/*") },
                                    modifier = androidx.compose.ui.Modifier
                                        .size(120.dp, 40.dp),
                                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                        androidx.compose.ui.graphics.Color.Blue
                                    )
                                )
                                {
                                    androidx.compose.material.Text(
                                        text = "SELECT",
                                        style = androidx.compose.ui.text.TextStyle(
                                            color = androidx.compose.ui.graphics.Color.White,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                        )
                                    )
                                }
                                androidx.compose.material.Button(
                                    onClick = {
                                        val file = java.io.File(
                                            filesDir,
                                            java.util.UUID.randomUUID().toString() + ".jpg"
                                        )
                                        uri = androidx.core.content.FileProvider.getUriForFile(
                                            applicationContext,
                                            "com.example.myact.fileprovider",
                                            file
                                        )
                                        take.launch(uri)
                                    },
                                    modifier = androidx.compose.ui.Modifier
                                        .size(120.dp, 40.dp)
                                        .padding(start = 20.dp),
                                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                                        androidx.compose.ui.graphics.Color.Blue
                                    )
                                )
                                {
                                    androidx.compose.material.Text(
                                        text = "TAKE  ",
                                        style = androidx.compose.ui.text.TextStyle(
                                            color = androidx.compose.ui.graphics.Color.White,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.size(30.dp))
                            Button(onClick = { Toast.makeText(applicationContext, "IM WORK!", Toast.LENGTH_SHORT).show()},
                                modifier = Modifier
                                    .size(200.dp, 40.dp)
                                    .align(CenterHorizontally)
                                ,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Cyan
                                )) {
                                Text(text = "PUT",style = TextStyle(color = Color.White, fontSize = 16.sp))
                            }
                            Spacer(modifier = Modifier.height(20.dp))
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



