package com.example.smartwaterbottle

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.smartwaterbottle.ui.theme.SmartWaterBottleTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartwaterbottle.helper.CustomCircularProgressIndicator
import com.example.smartwaterbottle.helper.customProgressBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartWaterBottleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {

    val currentTemp = remember{
        mutableStateOf(50f)
    }

    val currentPercentage = remember{
        mutableStateOf(20f)
    }

    LaunchedEffect(key1 = viewModel.currentTemp.collectAsState(initial = "0").value){
        viewModel.getCurrentTemperature()
        currentTemp.value = viewModel.currentTemp.value.toFloat()
        Log.d(TAG, "MainScreen: ${currentTemp.value}")
    }

    LaunchedEffect(key1 = viewModel.currentWaterLeft.collectAsState(initial = "0").value){
        viewModel.getCurrentPercentage()
        if(viewModel.currentWaterLeft.value.toFloat() < 0 || viewModel.currentWaterLeft.value.toFloat() > 20){
            currentPercentage.value = -1f
        }else{
            currentPercentage.value = 100 - viewModel.currentWaterLeft.value.toFloat() / 20 * 100
            Log.d(TAG, "MainScreen: ${currentPercentage.value}")
        }


    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally){
        CustomCircularProgressIndicator(
            modifier = Modifier
                .size(250.dp)
                .background(Color.White)
            ,
            positionValue = currentTemp,
            primaryColor = Color.Cyan,
            secondaryColor = Color.White,
            circleRadius = 170f,
            onPositionChange = {

            }
        )
        LinearProgressIndicator(progress = 0.5f)
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("SISA AIR SAAT INI", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            customProgressBar(currentPercentage)
        }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    SmartWaterBottleTheme {
//        Greeting("Android")
//    }
//}