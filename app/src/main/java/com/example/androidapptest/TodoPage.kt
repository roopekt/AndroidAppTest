package com.example.androidapptest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TodoPage(tasks: PersistentTaskList, modifier: Modifier = Modifier) {
    var newTask by remember {
        mutableStateOf("")
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            BasicTextField(
                value = newTask,
                onValueChange = { text ->
                    newTask = text.replace("\n", "")
                },
                singleLine = true,
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(5.dp))
                    .padding(5.dp)
                    .weight(1f, fill = true)
            )
            Button(
                onClick = {
                    if (newTask.isNotBlank()) {
                        tasks.add(newTask)
                        newTask = ""
                    }
                },
                modifier = Modifier
                    .padding(5.dp)
            ){
                Text("Add")
            }
        }
        Divider()
        TaskList(tasks)
    }
}

@Composable
fun TaskList(tasks: PersistentTaskList, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        itemsIndexed(tasks.trackedList) { i, task ->
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                IconButton(onClick = {
                    //relies on the fact that indices are recalculated by Jetpack when 'tasks' changes
                    tasks.remove(i)
                }) {
                    Icon(Icons.Rounded.Check, contentDescription = "$task done")
                }
                Text(task)
            }
        }
    }
}