package com.example.androidapptest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TodoPage(tasks: SnapshotStateList<String>, modifier: Modifier = Modifier) {
    var new_task by remember {
        mutableStateOf("")
    }

    Column(modifier) {
        Row {
            BasicTextField(
                value = new_task,
                onValueChange = { text ->
                    new_task = text.replace("\n", "")
                },
                singleLine = true,
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
            )
            Button(onClick = {
                if (new_task.isNotBlank()) {
                    tasks.add(new_task)
                    new_task = ""
                }
            }) {
                Text("Add")
            }
        }
        TaskList(tasks)
    }
}

@Composable
fun TaskList(tasks: SnapshotStateList<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        itemsIndexed(tasks) { i, task ->
            Row {
                IconButton(onClick = {
                    tasks.removeAt(i)
                }) {
                    Icon(Icons.Rounded.Check, contentDescription = "Done")
                }
                Text(task)
            }
        }
    }
}