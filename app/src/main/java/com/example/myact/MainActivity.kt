package com.example.myact

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat
import com.example.myact.JSON.DELETE
import com.example.myact.JSON.POST
import com.example.myact.ui.theme.Shapes
import com.example.myact.ui.theme.moy
import dev.chrisbanes.accompanist.glide.GlideImage
import java.io.ByteArrayOutputStream
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
            var a:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.img)
            var b = a.asImageBitmap()
            val check = Base64.encodeToString(image,Base64.NO_WRAP)
            if (check != "null"){
                a = BitmapFactory.decodeByteArray(image,0,image.size)
                b = a.asImageBitmap()
            }


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
                            modifier = Modifier
                                .align(TopCenter)
                                .verticalScroll(rememberScrollState())
                        ) {

                            if (urik.value != null){
                                val size = remember {
                                    mutableStateOf(240)
                                }
                                val sourse = ImageDecoder.createSource(contentResolver,urik.value!!)
                                val image = ImageDecoder.decodeBitmap(sourse)
                                Image(bitmap = image.asImageBitmap(),null,
                                    modifier = Modifier
                                        .size(240.dp)
                                        .align(CenterHorizontally)
                                        .padding(top = 30.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(onTap = { select.launch("image/*") },
                                                onLongPress = {
                                                    urik.value = null
                                                },
                                                onDoubleTap = {
                                                    val file = File(
                                                        filesDir,
                                                        UUID
                                                            .randomUUID()
                                                            .toString() + ".jpg"
                                                    )
                                                    uri = FileProvider.getUriForFile(
                                                        applicationContext,
                                                        "com.example.myact.fileprovider",
                                                        file
                                                    )
                                                    take.launch(uri)
                                                })
                                        })



                            }else{
                                Spacer(modifier = Modifier.size(20.dp))
                                Box(modifier = Modifier
                                    .height(240.dp)
                                    .width(240.dp)
                                    .fillMaxWidth()
                                    .align(CenterHorizontally)
                                    .background(Color.LightGray)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onTap = { select.launch("image/*") },
                                            onLongPress = {
                                                val file = File(
                                                    filesDir,
                                                    UUID
                                                        .randomUUID()
                                                        .toString() + ".jpg"
                                                )
                                                uri = FileProvider.getUriForFile(
                                                    applicationContext,
                                                    "com.example.myact.fileprovider",
                                                    file
                                                )
                                                take.launch(uri)
                                            })
                                    }
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

                            val date = remember {
                                mutableStateOf("")
                            }
                            val date1 = remember {
                                mutableStateOf("")
                            }
                            var dateB = remember {
                                mutableStateOf("")
                            }
                            var dateD = remember {
                                mutableStateOf("")
                            }
                            val calendar = Calendar.getInstance()
                            val datePickerDialog = DatePickerDialog(this@MainActivity,
                                { view, year, month, day ->
                                    var day0 = day.toString()
                                    var month0 = month.toString()
                                    when{
                                        day<10 -> day0 = "0$day"
                                        month<10 -> month0 = "0$month"
                                    }

                                    date.value = "$day0.$month0.$year"
                                    dateB.value = "$year-$month0-$day0"

                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )

                            val datePickerDialog1 = DatePickerDialog(this@MainActivity,
                                { view, year, month, day ->
                                    var day0 = day.toString()
                                    var month0 = month.toString()
                                    when{
                                        day<10 -> day0 = "0$day"
                                        month<10 -> month0 = "0$month"
                                    }
                                    date1.value = "$day0.$month0.$year"
                                    dateD.value = "$year-$month0-$day0"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )

                            Row(Modifier.align(CenterHorizontally)) {
                                OutlinedTextField(value = date.value, onValueChange = {
                                    date.value = it
                                },modifier = Modifier
                                    .pointerInput(Unit) {
                                        detectTapGestures(onTap = { datePickerDialog.show() })
                                    }
                                    .width(120.dp),colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Black,
                                    cursorColor = Color.Black
                                ), readOnly = true, label = { Text(text = "BirthDate", modifier = Modifier
                                    .pointerInput(Unit){
                                        detectTapGestures(onTap = {datePickerDialog.show()})
                                    })})
                                Spacer(modifier = Modifier.size(20.dp))
                                OutlinedTextField(value = date1.value, onValueChange = {
                                       date1.value = it
                                },modifier = Modifier
                                    .pointerInput(Unit) {
                                        detectTapGestures(onTap = { datePickerDialog1.show() })
                                    }
                                    .width(120.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Black,
                                    cursorColor = Color.Black
                                ), readOnly = true,label = { Text(text = "DeadDate", modifier = Modifier
                                    .pointerInput(Unit){
                                        detectTapGestures(onTap = {datePickerDialog1.show()})
                                    })})
                            }
                            Spacer(modifier = Modifier.size(30.dp))

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



                            Spacer(modifier = Modifier.size(20.dp))

                            val er1 = remember {
                                mutableStateOf(false)
                            }
                            if (er1.value==true){
                                errorDialog(mes = "Поле Surname не должно быть пустым")
                            }

                            val er2 = remember {
                                mutableStateOf(false)
                            }
                            if (er2.value==true){
                                errorDialog(mes = "Поле Name не должно быть пустым")
                            }

                            val er3 = remember {
                                mutableStateOf(false)
                            }
                            if (er3.value==true){
                                errorDialog(mes = "Поле 2nd Name не должно быть пустым")
                            }

                            Button(onClick = {
                                             if (ed1.value == ""){
                                                 er1.value  = true
                                                 return@Button
                                             }
                                if (ed2.value == ""){
                                    er2.value  = true
                                    return@Button

                                }
                                if (ed3.value == ""){
                                er3.value  = true
                                return@Button
                            }

                                var string:String = "null"
                            if (urik.value != null){
                                val sourse = ImageDecoder.createSource(contentResolver,urik.value!!)
                                val bitmaP = ImageDecoder.decodeBitmap(sourse)
                                val stream = ByteArrayOutputStream()
                                bitmaP.compress(Bitmap.CompressFormat.JPEG,10,stream)
                                string = Base64.encodeToString(stream.toByteArray(),Base64.NO_WRAP)

                            }
                                POST.put(applicationContext,id,ed1.value,ed2.value,
                                    ed3.value,ed4.value,dateB.value,dateD.value,string)
                            },
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
        }else{

        }


    }

    @Composable
    fun errorDialog(mes:String){
        val close = remember {
            mutableStateOf(true)
        }
        if (close.value == true){
            AlertDialog(onDismissRequest = {close.value = false}, title = { Text(text = "ERROR")} ,buttons = {
                    Column() {
                        Spacer(modifier = Modifier.size(50.dp))
                        Text(text = mes,modifier = Modifier.align(CenterHorizontally),
                            fontSize = 16.sp)
                        Spacer(modifier = Modifier.size(50.dp))
                        Button(onClick = {close.value = false},
                            Modifier
                                .align(CenterHorizontally)
                                .size(120.dp, 40.dp)
                                .border(4.dp, Color.Black), colors = ButtonDefaults
                                .buttonColors(
                                    backgroundColor = Color.White
                                )) {
                            Text(text = "OK", fontSize = 16.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.size(40.dp))
                }
            })
        }
    }

}



