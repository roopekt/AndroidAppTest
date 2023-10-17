package com.example.androidapptest

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

@Composable
fun getPersistentTaskList(fileName: String): PersistentTaskList {
    val trackedTaskList = remember {
        mutableStateListOf<String>()
    }
    return PersistentTaskList(fileName, trackedTaskList, LocalContext.current)
}

class PersistentTaskList(fileName: String, trackedTaskList: SnapshotStateList<String>, context: Context) {
    // do not modify directly, use the methods below
    val trackedList = trackedTaskList

    private val fileName = fileName
    private val context = context

    init {
        loadFromFile()
    }

    fun add(task: String) {
        trackedList.add(task)
        saveToFile()
    }

    fun remove(index: Int) {
        trackedList.removeAt(index)
        saveToFile()
    }

    private fun saveToFile() {
        val json = Json.encodeToString(trackedList.toList())

        val file = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        file.bufferedWriter().use { writer ->
            writer.write(json)
        }
        file.close()
    }

    private fun loadFromFile() {
        if (!File(context.filesDir, fileName).exists())
            return

        val json: String
        val file = context.openFileInput(fileName)
        file.bufferedReader().use { reader ->
            json = reader.readText()
        }
        file.close()

        val newTasks = Json.decodeFromString<List<String>>(json)
        trackedList.clear()
        trackedList.addAll(newTasks)
    }
}