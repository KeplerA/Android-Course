package com.example.helloapp.model

import android.graphics.Color
import org.json.JSONObject
import java.util.UUID

data class Note(
    val uid: UUID = UUID.randomUUID(),
    val title: String,
    val content: String,
    val color: Int = Color.WHITE,
    val importance: Importance
) {
    val json:JSONObject get() {
        val result = JSONObject()
        result.put("uid", uid.toString())
        result.put("title", title)
        result.put("content", content)
        if (color != Color.WHITE) {
            result.put("color", color)
        }
        if (importance != Importance.NORMAL) {
            result.put("importance", importance)
        }
        return result
    }
    companion object {
        fun parse(json: JSONObject): Note {
            val uid = json.optString("uid", UUID.randomUUID().toString())
            val title = json.getString("title")
            val content = json.getString("content")
            val color = json.optInt("color", Color.WHITE)
            val importance = json.optString("importance", "NORMAL")
            return Note(UUID.fromString(uid), title, content, color, Importance.from(importance))
        }
    }
}

enum class Importance {
    IMPORTANT,
    NORMAL,
    UNIMPORTANT;

    companion object {
        fun from(type: String?): Importance = entries.find { it.name == type } ?: NORMAL
    }
}



