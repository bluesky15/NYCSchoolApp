package d11012025.lalitbehera.nycschools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import d11012025.lalitbehera.nycschools.ui.theme.NYCSchoolsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolsTheme {
                MainView()
            }
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()
    val viewModel: NYCViewModel = hiltViewModel()
    Scaffold(modifier = Modifier.fillMaxSize()) {
        NavHost(
            startDestination = "first",
            navController = navController,
            modifier = Modifier.padding(it)
        ) {
            composable("first") { FirstScreen(navController, viewModel) }
            composable("second") { SecondScreen(viewModel) }
        }
    }
}

@Composable
fun FirstScreen(navController: NavController, viewModel: NYCViewModel) {
    val schoolData = viewModel.schoolListData.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        when (schoolData.value) {
            is NetworkResult.ERROR -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Error!") }

            }

            is NetworkResult.LOADING -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text("Loading...") }

            }

            is NetworkResult.SUCCESS -> {
                val list =
                    (schoolData.value as NetworkResult.SUCCESS<List<SchoolDataResponse>>).data
                LazyColumn {
                    items(list) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 2.dp)
                        ) {
                            Text("School Name:")
                            Text(it.schoolName)
                            Text("Address:")
                            Text("${it.city}, ${it.zip}")
                            Button(onClick = {
                                viewModel.selectedID = it.id
                                navController.navigate("second")
                            }) { Text("Show Details") }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SecondScreen(viewModel: NYCViewModel) {
    LaunchedEffect(Unit) { viewModel.filterData() }
    Box(modifier = Modifier.fillMaxSize()) {
        val data = viewModel.schoolSatListData.collectAsState(null)
        if (data.value?.id?.isNotEmpty() == true) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text("School Name:")
                Text("${data.value?.schoolName}")
                Text("Sat Math Scores:")
                Text("${data.value?.satMathAvgScore}")
                Text("Sat Writing Scores:")
                Text("${data.value?.satWritingAvgScore}")
                Text("Number of Sat Takers:")
                Text("${data.value?.numberOfSatTakers}")
                Text("Critical writing sat score:")
                Text("${data.value?.satCriticalReadingAverageScore}")
            }
        }

    }
}