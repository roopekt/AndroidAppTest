package com.example.androidapptest

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

        if (checkSelfPermission("android.permission.FOREGROUND_SERVICE") == PackageManager.PERMISSION_DENIED)
            requestPermissions(arrayOf("android.permission.FOREGROUND_SERVICE"), 0)
        if (checkSelfPermission("android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_DENIED)
            requestPermissions(arrayOf("android.permission.POST_NOTIFICATIONS"), 0)
        TaskNotificationService.startService(this)
    }
}

@Composable
fun FullApp(modifier: Modifier = Modifier) {
    val tasks = getPersistentTaskList(fileName = "tasks.json")
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
    val persistentTaskList = PersistentTaskList("foo", tasks, LocalContext.current)

    AndroidAppTestTheme {
        TodoPage(persistentTaskList)
    }
}