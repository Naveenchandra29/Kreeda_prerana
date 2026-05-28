package com.example.kreedaprerana

import android.os.Bundle
import android.graphics.Color.rgb
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import android.os.SystemClock
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            KreedaPreranaApp()
        }
    }
}

data class Athlete(
    val name: String,
    val age: String,
    val sport: String,
    val score: Double,
    val completed: Boolean = false
)

@Composable
fun KreedaPreranaApp() {

    val context = LocalContext.current

    /*
    LOGIN
     */

    var username by remember { mutableStateOf("") }

    var isLoggedIn by remember { mutableStateOf(false) }

    /*
    STUDENT ENTRY
     */

    var athleteName by remember { mutableStateOf("") }

    var athleteAge by remember { mutableStateOf("") }

    var selectedSport by remember {
        mutableStateOf("Athletics")
    }

    /*
    STUDENT STORAGE
     */

    var athletes by remember {

        mutableStateOf(

            mutableListOf<Athlete>()
        )
    }

    /*
    CURRENT STUDENT
     */

    var currentIndex by remember {

        mutableStateOf(0)
    }

    /*
    TIMER
     */

    var running by remember {

        mutableStateOf(false)
    }

    var time by remember {

        mutableStateOf(0L)
    }

    var startTime by remember {

        mutableStateOf(0L)
    }

    LaunchedEffect(running) {

        while (running) {

            time =
                SystemClock.elapsedRealtime() - startTime

            delay(10)
        }
    }

    val seconds =
        String.format("%.2f", time / 1000f)

    /*
    LOGIN SCREEN
     */

    if (!isLoggedIn) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.sports_bg
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor =
                            Color(0xCC0D47A1)
                    ),

                    shape = RoundedCornerShape(28.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "🏆 Kreeda-Prerana",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text =
                                "Grassroots Sports Talent Scout",

                            color = Color.White
                        )

                        Spacer(
                            modifier = Modifier.height(25.dp)
                        )

                        OutlinedTextField(
                            value = username,

                            onValueChange = {
                                username = it
                            },

                            label = {
                                Text("Enter Username")
                            },

                            textStyle = TextStyle(
                                color = Color.White
                            ),

                            colors =
                                OutlinedTextFieldDefaults.colors(

                                    focusedTextColor =
                                        Color.White,

                                    unfocusedTextColor =
                                        Color.White,

                                    focusedLabelColor =
                                        Color.White,

                                    unfocusedLabelColor =
                                        Color.LightGray,

                                    cursorColor =
                                        Color.White
                                )
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Button(
                            onClick = {

                                if (username.isNotEmpty()) {

                                    isLoggedIn = true

                                    Toast.makeText(
                                        context,
                                        "Welcome Coach $username",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {

                                    Toast.makeText(
                                        context,
                                        "Enter Username",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Color(0xFFFF9800)
                                )
                        ) {

                            Text(
                                text = "ENTER SCOUT PORTAL",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    } else {

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE3F2FD))
                .padding(16.dp)

        ) {

            item {

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "🏆 Kreeda-Prerana",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Text(
                    text = "Welcome Coach $username",
                    color = Color.DarkGray
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                ATHLETE PROFILE
                 */

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    shape = RoundedCornerShape(24.dp)

                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "👟 Athlete Batch Entry",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Image(
                            painter = painterResource(
                                id = R.drawable.school_sports
                            ),

                            contentDescription = null,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                ),

                            contentScale =
                                ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        OutlinedTextField(
                            value = athleteName,

                            onValueChange = {
                                athleteName = it
                            },

                            label = {
                                Text("Athlete Name")
                            },

                            modifier =
                                Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        OutlinedTextField(
                            value = athleteAge,

                            onValueChange = {
                                athleteAge = it
                            },

                            label = {
                                Text("Age")
                            },

                            modifier =
                                Modifier.fillMaxWidth()
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Text(
                            text = "Primary Sport",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Row {

                            Button(
                                onClick = {
                                    selectedSport =
                                        "Athletics"
                                },

                                colors =
                                    ButtonDefaults.buttonColors(

                                        containerColor =

                                            if (
                                                selectedSport ==
                                                "Athletics"
                                            )

                                                Color.Blue

                                            else

                                                Color.Gray
                                    )
                            ) {

                                Text("Athletics")
                            }

                            Spacer(
                                modifier = Modifier.width(10.dp)
                            )

                            Button(
                                onClick = {
                                    selectedSport =
                                        "Kabaddi"
                                },

                                colors =
                                    ButtonDefaults.buttonColors(

                                        containerColor =

                                            if (
                                                selectedSport ==
                                                "Kabaddi"
                                            )

                                                Color.Blue

                                            else

                                                Color.Gray
                                    )
                            ) {

                                Text("Kabaddi")
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Button(

                            onClick = {

                                if (
                                    athleteName.isNotEmpty() &&
                                    athleteAge.isNotEmpty()
                                ) {

                                    athletes.add(

                                        Athlete(
                                            name = athleteName,
                                            age = athleteAge,
                                            sport = selectedSport,
                                            score = 0.0,
                                            completed = false
                                        )
                                    )

                                    athleteName = ""
                                    athleteAge = ""

                                    Toast.makeText(
                                        context,
                                        "Student Added Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },

                            modifier =
                                Modifier.fillMaxWidth(),

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Color(0xFF0D47A1)
                                )
                        ) {

                            Text(
                                text = "ADD STUDENT",
                                color = Color.White
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Text(
                            text =
                                "Students Added : ${athletes.size}",

                            fontWeight = FontWeight.Bold,

                            color = Color(0xFF2E7D32)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                TRIAL LOGGER
                 */

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFBBDEFB)
                    ),

                    shape = RoundedCornerShape(24.dp)

                ) {

                    Column(

                        modifier = Modifier.padding(18.dp),

                        horizontalAlignment =
                            Alignment.CenterHorizontally

                    ) {

                        Text(
                            text = "⏱ Trial Logger",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Image(
                            painter = painterResource(
                                id = R.drawable.running_track
                            ),

                            contentDescription = null,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                ),

                            contentScale =
                                ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        if (
                            athletes.isNotEmpty() &&
                            currentIndex < athletes.size
                        ) {

                            Text(
                                text =
                                    "Current Student : ${athletes[currentIndex].name}",

                                fontSize = 22.sp,

                                fontWeight = FontWeight.Bold,

                                color = Color.Black
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )
                        }

                        Text(
                            text = "$seconds sec",
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Row {

                            Button(

                                onClick = {

                                    startTime =
                                        SystemClock.elapsedRealtime()

                                    running = true
                                },

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            Color.Green
                                    )
                            ) {

                                Text("START")
                            }

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

                            Button(

                                onClick = {

                                    running = false

                                    if (
                                        athletes.isNotEmpty() &&
                                        currentIndex < athletes.size
                                    ) {

                                        val updatedList =
                                            athletes.toMutableList()

                                        updatedList[currentIndex] =

                                            updatedList[currentIndex]
                                                .copy(

                                                    score =
                                                        seconds.toDouble(),

                                                    completed = true
                                                )

                                        athletes = updatedList

                                        if (
                                            currentIndex <
                                            athletes.size - 1
                                        ) {

                                            currentIndex++
                                        }

                                        time = 0L
                                    }
                                },

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            Color.Red
                                    )
                            ) {

                                Text("STOP")
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Text(
                            text =
                                "Timer Accurate To Two Decimal Places",

                            color = Color.Black
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                TALENT CURVE
                 */

                /*
REAL TIME TALENT CURVE GRAPH
 */

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    shape = RoundedCornerShape(24.dp)

                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "📈 Real-Time Talent Curve",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        AndroidView(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp),

                            factory = { context ->

                                LineChart(context).apply {

                                    layoutParams =
                                        android.view.ViewGroup.LayoutParams(
                                            MATCH_PARENT,
                                            MATCH_PARENT
                                        )

                                    description.isEnabled = false

                                    setTouchEnabled(true)

                                    setPinchZoom(true)

                                    setScaleEnabled(true)

                                    animateX(1500)

                                    xAxis.position =
                                        XAxis.XAxisPosition.BOTTOM

                                    axisRight.isEnabled = false

                                    legend.isEnabled = true
                                }
                            },

                            update = { chart ->

                                val entries =
                                    ArrayList<Entry>()

                                athletes
                                    .sortedBy { it.score }
                                    .forEachIndexed { index, athlete ->

                                        if (athlete.completed) {

                                            entries.add(

                                                Entry(
                                                    index.toFloat(),
                                                    athlete.score.toFloat()
                                                )
                                            )
                                        }
                                    }

                                val dataSet = LineDataSet(
                                    entries,
                                    "Sprint Timing Performance"
                                )

                                dataSet.color =
                                    rgb(13, 71, 161)

                                dataSet.valueTextColor =
                                    rgb(0, 0, 0)

                                dataSet.lineWidth = 3f

                                dataSet.circleRadius = 6f

                                dataSet.setCircleColor(
                                    rgb(255, 87, 34)
                                )

                                dataSet.valueTextSize = 12f

                                val lineData =
                                    LineData(dataSet)

                                chart.data = lineData

                                chart.invalidate()
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text =
                                "Lower Timing Indicates Better Athletic Performance",

                            color = Color.DarkGray,

                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                LEADERBOARD
                 */

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    shape = RoundedCornerShape(24.dp)

                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "📊 School Leaderboard",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0D47A1)
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Image(
                            painter = painterResource(
                                id = R.drawable.leaderboard
                            ),

                            contentDescription = null,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(
                                    RoundedCornerShape(20.dp)
                                ),

                            contentScale =
                                ContentScale.Crop
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        athletes

                            .sortedBy { it.score }

                            .forEachIndexed { index, athlete ->

                                Card(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),

                                    colors =
                                        CardDefaults.cardColors(

                                            containerColor =

                                                if (index == 0)

                                                    Color(0xFFFFF59D)

                                                else

                                                    Color(0xFFE3F2FD)
                                        )
                                ) {

                                    Column(
                                        modifier =
                                            Modifier.padding(14.dp)
                                    ) {

                                        Text(
                                            text =
                                                "${index + 1}. ${athlete.name}",

                                            fontWeight =
                                                FontWeight.Bold
                                        )

                                        Text(
                                            text =
                                                "Age : ${athlete.age}"
                                        )

                                        Text(
                                            text =
                                                "Sport : ${athlete.sport}"
                                        )

                                        Text(
                                            text =
                                                "Sprint Time : ${athlete.score} sec"
                                        )

                                        Text(

                                            text =

                                                if (
                                                    athlete.completed
                                                )

                                                    "✅ Trial Completed"

                                                else

                                                    "⌛ Pending",

                                            color =

                                                if (
                                                    athlete.completed
                                                )

                                                    Color(0xFF2E7D32)

                                                else

                                                    Color.Red,

                                            fontWeight =
                                                FontWeight.Bold
                                        )
                                    }
                                }
                            }
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                /*
                IMPACT GOALS
                 */

                Card(

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFD1C4E9)
                    ),

                    shape = RoundedCornerShape(24.dp)

                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "🌍 Impact Goals",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4527A0)
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text =
                                "• Identifies Rural Sports Talent Early",

                            color = Color.Black
                        )

                        Text(
                            text =
                                "• Supports Khelo India Vision",

                            color = Color.Black
                        )

                        Text(
                            text =
                                "• Encourages Fitness & Competition",

                            color = Color.Black
                        )

                        Text(
                            text =
                                "• Builds Equal Sports Opportunities",

                            color = Color.Black
                        )

                        Text(
                            text =
                                "• Batch Entry For 30+ Students",

                            color = Color.Black
                        )

                        Text(
                            text =
                                "• AI-Based Talent Curve Monitoring",

                            color = Color.Black
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}