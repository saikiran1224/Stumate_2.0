package com.kirandroid.stumate20.onboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.kirandroid.stumate20.navigation.SetupNavGraph
import com.kirandroid.stumate20.ui.theme.Stumate20Theme
import com.kirandroid.stumate20.utils.UserPreferences
import com.kirandroid.stumate20.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Stumate20Theme {
                
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)

                val viewModel = HomeScreenViewModel()
                // Context
                val context = LocalContext.current
                // Instantiating User Preferences class
                val dataStore = UserPreferences(context = context)

                var _studentBatchID by remember { mutableStateOf("") }
                val studBatchID = dataStore.getStudentAcademicBatch.collectAsState(initial = "").value.toString()
                _studentBatchID = studBatchID

                viewModel.listenToSubjects(_studentBatchID)
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }*/
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Stumate20Theme {
        Greeting("Android")
    }
}*/
