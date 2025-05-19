package com.example.helloapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoteContent() {
    var title by rememberSaveable{ mutableStateOf<String?>(null) }
    var content by rememberSaveable{ mutableStateOf<String?>(null) }
    val selectedColor = LocalSelectedColor.current

    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(30.dp))
        .fillMaxWidth()
        .background(selectedColor.base)
        .padding(20.dp)
    ){
        Column{
            TextField(
                value = title ?: "",
                {title = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                textStyle = TextStyle(fontSize = 18.sp),
                placeholder = { Text("Название", fontSize = 18.sp) },
                maxLines = 3,
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = selectedColor.lightest,
                    unfocusedContainerColor = selectedColor.lightest,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(10.dp))

            TextField(
                value = content ?: "",
                {content = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                textStyle = TextStyle(fontSize = 12.sp),
                placeholder = { Text("Текст", fontSize = 12.sp) },
                maxLines = 20,
                shape = RoundedCornerShape(15.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = selectedColor.lightest,
                    unfocusedContainerColor = selectedColor.lightest,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )

            )
        }
    }
}