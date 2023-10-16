package com.example.androidapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidapptest.ui.theme.AndroidAppTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAppTestTheme {
                FullApp()
            }
        }
    }
}

@Composable
fun FullApp(modifier: Modifier = Modifier) {
    val tasks = remember {
        mutableStateListOf<String>()
    }
    AndroidAppTestTheme {
        TodoPage(tasks, modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val tasks = remember {
        mutableStateListOf<String>("hello", "world", "roses are red")
    }

    AndroidAppTestTheme {
        TodoPage(tasks)
    }
}