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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import java.util.*

var arraY :JSONArray? = null
class MainActivity : ComponentActivity() {
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
        var photo = remember{
            mutableListOf<String>()
        }
        val count = remember {
            mutableStateOf(0)
        }
        GET.testGet{
                array ->
            hi.value = array.getJSONObject(0).getString("firstName")
            count.value = array.length()
            for (i in 0 until count.value){
                namE.add(array.getJSONObject(i).getString("firstName"))
                if (array.getJSONObject(i).getString("photo").isNotEmpty()){
                    photo.add(array.getJSONObject(i).getString("photo"))
                }
            }
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
                        if (namE.isEmpty() || photo.isEmpty()){

                        }else{
                            item {
                                val array = Base64.decode(photo[i],Base64.NO_WRAP)
                                Chel(namE[i],array)
                            }
                        }

                    }

                }
            }
            
        }
            
        

    }

    @Composable
    fun Chel(name:String,image:ByteArray) {

        val a = BitmapFactory.decodeByteArray(image,0,image.size)
        val b = a.asImageBitmap()

        Box(
            Modifier
                .size(400.dp,)
                .fillMaxWidth()) {
            Column(Modifier.fillMaxSize()) {
                Image(bitmap = b,null, modifier = Modifier
                    .size(150.dp,200.dp)
                    .align(CenterHorizontally)
                    )
                Text(text = name,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 50.dp))
            }


        }
    }


}



