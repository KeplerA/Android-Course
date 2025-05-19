package com.example.helloapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helloapp.model.NoteColorPalette
import com.example.helloapp.model.banana
import com.example.helloapp.model.blueberry
import com.example.helloapp.model.gray
import com.example.helloapp.model.lime
import com.example.helloapp.model.raspberry
import com.example.helloapp.model.strawberry

@Composable
fun NoteColor(
    selectedColor: NoteColorPalette,
    onSelectedColorChange: (NoteColorPalette) -> Unit,
    custom: NoteColorPalette,
    onCustomChange: (NoteColorPalette) -> Unit
) {
    val openColorPickerDialog = remember { mutableStateOf(false) }
    val noteColors = listOf(gray, banana, lime, blueberry, raspberry, strawberry)
    val brush = Brush.sweepGradient(listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red))
    Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
        Text("Цвет заметки")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            noteColors.forEach { color ->
                val selected = selectedColor == color
                Box (contentAlignment = Alignment.Center){
                    Box(modifier = Modifier
                        .background(
                            color.light,
                            shape = RoundedCornerShape(percent = 50)
                        )
                        .size(40.dp)
                        .selectable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            selected = selected,
                            onClick = {
                                onSelectedColorChange(color)
                            }
                        )
                        .border(
                            width = if (selected) 3.dp else 1.dp,
                            color = if (selected) color.base else color.dark,
                            shape = RoundedCornerShape(50)
                        )
                    )
                    if (selected) {
                        Icon(
                            Icons.Default.Check,
                            modifier = Modifier.size(30.dp),
                            contentDescription = null,
                            tint = Color(0x60000000)
                        )
                    }
                }
            }

            val selected = selectedColor == custom
            Box(contentAlignment = Alignment.Center){
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(brush, shape = RoundedCornerShape(percent = 50))
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color(0xFFFFFFFF),
                                    Color(0x00FFFFFF)
                                )
                            ), shape = RoundedCornerShape(percent = 50)
                        )
                        .selectable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            selected = selected,
                            onClick = {}
                        )
                        .border(
                            width = if (selected) 3.dp else 3.dp,
                            color = if (selected) Color(0x80000000) else Color(0x80FFFFFF),
                            shape = RoundedCornerShape(50)
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    openColorPickerDialog.value = true
                                }
                            )
                        }
                )

                if (selected) {
                    Icon(
                        Icons.Default.Check,
                        modifier = Modifier.size(30.dp),
                        contentDescription = null,
                        tint = Color(0x60000000)
                    )
                }
            }
        }
    }
    if (openColorPickerDialog.value) {
        CustomColorPicker(
            onCustomChange = {onCustomChange(it)},
            onConfirm = {onSelectedColorChange(custom)},
            onDismiss = {openColorPickerDialog.value = false}
        )
    }
}

@Composable
fun CustomColorPicker(
    onCustomChange: (NoteColorPalette) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var pickedColor: Color? by remember { mutableStateOf(null) }
    val rgb = mutableListOf(0, 255, 0)
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выбор кастомного цвета") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row{
                    val color = pickedColor
                    if(color == null) {
                        Box(modifier = Modifier
                            .size(40.dp)
                            .background(Color.LightGray),
                            contentAlignment = Alignment.Center){
                            Text("PICK\nCOLOR",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center,
                                lineHeight = 12.sp)
                        }
                    } else {
                        Box(
                            Modifier
                                .size(40.dp)
                                .background(color))
                    }
                }
                Spacer(Modifier.size(30.dp))
                Row{

                    var sign = 1
                    for (i in 0..5) {
                        for (j in 0..14) {
                            Column{
                                for (k in 0..60) {
                                    val color = Color(
                                        rgb[0] - k * (rgb[0] - 127) / 60,
                                        rgb[1] - k * (rgb[1] - 127) / 60,
                                        rgb[2] - k * (rgb[2] - 127) / 60
                                    )
                                    Box(
                                        Modifier
                                        .size(width = 3.dp, height = 3.dp)
                                        .background(color)
                                        .selectable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(),
                                            selected = pickedColor == color,
                                            onClick = {
                                                pickedColor = color
                                                onCustomChange(generateColorShades(color))
                                            }
                                        )
                                    )
                                }
                            }
                            rgb[i % 3] += 17 * sign
                        }
                        sign *= -1
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm()
                onDismiss()
                },
                enabled = (pickedColor != null)
            ) {
                Text("ОК")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text("Отменить")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}