package com.evanemran.kmpfolio.android

import android.os.Bundle
import android.view.Surface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evanemran.kmpfolio.Greeting
import com.evanemran.kmpfolio.bio
import com.evanemran.kmpfolio.daysUntilNewYear
import com.evanemran.kmpfolio.name
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var selectedIndex: Int = 0
        setContent {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            MyApplicationTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.background),
                            contentScale = ContentScale.FillWidth
                        ),
//                    color = Color.Black,
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "Portfolio", color = Color.White) },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {

                                            scope.launch {
                                                scaffoldState.drawerState.open()
                                            }
                                        },
                                    ) {
                                        Icon(
                                            Icons.Rounded.Menu,
                                            tint = Color.White,
                                            contentDescription = ""
                                        )
                                    }
                                },
                                backgroundColor = Color.Black
                            )
                        },
                        drawerContent = { DrawerView() },
                    ) { Body() }
                }
            }
        }
    }
}

@Composable
fun Greeting(text: String) {
    Text(
        text = text,
        style = TextStyle(color = Color.Green, fontSize = 28.sp),
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun profileImage() {
    Image(
        painter = painterResource(id = R.drawable.me),
        contentDescription = stringResource(id = R.string.me),
        modifier = Modifier
            .padding(20.dp)
            .width(200.dp)
            .height(200.dp)
    )
}

@Composable
fun Hello(text: String) {
    Text(
        text = text,
        style = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 16.sp),
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun Headline(text: String) {
    Text(
        text = text,
        style = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 22.sp),
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun AddDrawerHeader(
    title: String,
    titleColor: Color = Color.White,
) {
    Card(
        backgroundColor = Color.Black,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.Black)
            .padding(16.dp),
        border = BorderStroke(0.dp, color = Color.Black),


        ) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = titleColor
            ),
            modifier = Modifier.padding(14.dp)
        )

    }
}

@Composable
fun Body() {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillWidth
            ), color = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.background(Color.Black)
        ) {
            profileImage()
            Headline(name())
            Hello(bio())
            SocialButtons()
        }
    }
}

@Composable
fun OtherBody(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillWidth
            ), color = Color.Black
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.background(Color.Black)
        ) {
            Text(title, style = TextStyle(color = Color.White))
        }
    }
}

@Composable
fun DrawerView() {
    val navMenu = listOf("Home", "About", "Resume", "Services", "Portfolio", "Contact")
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Black)
    ) {
        item {
            AddDrawerHeader(title = "Know more.")
        }

        items(navMenu.size) { index ->
            AddDrawerContentView(title = navMenu[index], selected = true)
        }
    }

}

@Composable
fun SocialButtons() {
    Row(modifier = Modifier.padding(16.dp)) {
        RoundedButton(image = R.drawable.twitter, title = "twitter")
        RoundedButton(image = R.drawable.facebook, title = "facebook")
        RoundedButton(image = R.drawable.instagram, title = "instagram")
        RoundedButton(image = R.drawable.linkedin, title = "linkedin")
    }
}

@Composable
fun RoundedButton(image: Int, title: String) {
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp),
        shape = CircleShape,
        /*border = BorderStroke(0.dp, Color(0XFF0F9D58)),*/
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Gray,
            backgroundColor = Color.DarkGray
        ),
    ) {
        // Adding an Icon "Add" inside the Button
//        Icon(
//            Icons.Default.Add,
//            contentDescription = "content description",
//            tint = Color(0XFF0F9D58)
//        )

        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = R.string.me),
            colorFilter = ColorFilter.tint(color = Color.White),
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        )

    }
}

@Composable
fun AddDrawerContentView(title: String, selected: Boolean) {
    val mContext = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.Black),
    ) {

        if (title.isNotEmpty()) {

            Button(
                onClick = {
                    Toast.makeText(mContext, title, Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )

            }

            /*if (selected)
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Green
                    )
                )
            else
                Text(text = title, modifier = Modifier.weight(1f), fontSize = 12.sp)*/
        }

    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Hello, Android!")
    }
}
