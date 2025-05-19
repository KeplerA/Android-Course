package com.example.helloapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloapp.model.Importance

@Composable
fun NoteImportance() {
    val importance = listOf(Importance.UNIMPORTANT, Importance.NORMAL, Importance.IMPORTANT)
    val importanceName = mapOf(Pair(Importance.UNIMPORTANT, "Неважная"), Pair(Importance.NORMAL, "! Обычная !"), Pair(
        Importance.IMPORTANT, "!! Важная !!"))
    val selectedImportance = remember { mutableStateOf(Importance.NORMAL) }

    val selectedColor = LocalSelectedColor.current

    Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
        Text("Важность")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            importance.forEach { importance ->
                val selected = selectedImportance.value == importance
                Box(modifier = Modifier
                    .background(
                        color = if (selected) selectedColor.lightest else selectedColor.base,
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .selectable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        selected = selected,
                        onClick = {
                            selectedImportance.value = importance
                        }
                    )
                    .border(
                        width = 3.dp,
                        color = selectedColor.base,
                        shape = RoundedCornerShape(50)
                    )
                ) {
                    Text("${importanceName[importance]}",
                        Modifier
                            .padding(8.dp)
                            .align(Alignment.Center))
                }
            }
        }
    }
}