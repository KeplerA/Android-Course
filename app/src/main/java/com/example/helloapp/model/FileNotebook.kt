package com.example.helloapp.model

import android.content.Context
import android.graphics.Color
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.UUID

class FileNotebook(
    private val context: Context
){
    var notebook = mutableMapOf<String, Note>()
        private set

    val log = LoggerFactory.getLogger(FileNotebook::class.java)

    fun addNewNote(note: Note) {
        val uid = note.uid.toString()
        notebook[uid] = note
        log.info("Added new note by UID: $uid")
    }

    fun createAndAddNewNote(
        uid: UUID = UUID.randomUUID(),
        title: String,
        content: String,
        color: Int = Color.WHITE,
        importance: Importance
    ) {
        notebook[uid.toString()] = Note(uid, title, content, color, importance)
        log.info("Added new note with UID: ${uid.toString()}")
    }

    fun removeNoteByUid(uid: UUID) {
        notebook.remove(uid.toString())
        log.info("Deleted note with UID: ${uid.toString()}")
    }

    fun saveNotebookToFile(fileNotebook: FileNotebook) {
        val filesDir = context.filesDir // /data/data/ваш.package/files
        val file = File(filesDir, "Notebook.txt")
        val json = JSONObject()
        val array = JSONArray()
        for (note in fileNotebook.notebook.values) {
            array.put(note.json)
        }
        json.put("notes", array)
        try {
            file.bufferedWriter().use {
                it.append(json.toString())
            }
            log.info("Notebook was saved to $filesDir/Notebook.txt")
        }
        catch (th: Throwable) {
            Log.e("FileNotebook", "NoteBook write failed")
        }
    }

    fun loadNotebookFromFile(fileDir: String, fileName: String) {
        val file = File(fileDir, fileName)
        try {
            val inputStream: InputStream = file.inputStream()
            inputStream.bufferedReader().use {
                val json = JSONObject(file.readText())
                val array = json.getJSONArray("notes")
                for (i in 0..<array.length()) {
                    val notejson = array.getJSONObject(i)
                    val note = Note.parse(notejson)
                    notebook[note.uid.toString()] = note
                }
            }
            log.info("Notebook was loaded from $fileDir/$fileName")
        } catch (error: IOException) {
            Log.e("FileNotebook", "can t read file $file.path", error)
            return
        }
    }
}