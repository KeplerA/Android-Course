package com.example.helloapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.helloapp.model.Importance
import com.example.helloapp.model.NoteColorPalette
import com.example.helloapp.model.banana
import com.example.helloapp.model.blueberry
import com.example.helloapp.model.gray
import com.example.helloapp.model.lime
import com.example.helloapp.model.raspberry
import com.example.helloapp.model.strawberry
import com.example.helloapp.ui.theme.HelloAppTheme
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HelloAppTheme {
                Scaffold {
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        NoteEditScreen(
                            Modifier
                                .background(selectedColor.light)
                                .padding(it))
                        val log = LoggerFactory.getLogger(MainActivity::class.java)
                        log.info("logger operation check")
                    }
                }
            }
        }
    }
}

var selectedColor by mutableStateOf(gray)

var custom : NoteColorPalette = NoteColorPalette(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified)

@Composable
fun NoteEditScreen(modifier: Modifier = Modifier) {
    Column (modifier = modifier.then(
        Modifier
            .fillMaxSize()
            .padding(10.dp))) {
        NoteContent()
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(10.dp))
        NoteDestructionDate()
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(10.dp))
        NoteColor()
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(10.dp))
        NoteImportance()
    }
}

@Composable
fun NoteContent() {
    var title by rememberSaveable{mutableStateOf("")}
    var content by rememberSaveable{mutableStateOf("")}

    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(30.dp))
        .fillMaxWidth()
        .background(selectedColor.base)
        .padding(20.dp)
        ){
        Column{
            TextField(
                title,
                {title = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                textStyle = TextStyle(fontSize = 18.sp),
                placeholder = {Text("Название", fontSize = 18.sp)},
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
                content,
                {content = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                textStyle = TextStyle(fontSize = 12.sp),
                placeholder = {Text("Текст", fontSize = 12.sp)},
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

@Composable
fun NoteDestructionDate() {
    val checkedState = remember { mutableStateOf(false) }
    val openDateDialog = remember { mutableStateOf(false) }
    val openTimeDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf<Long?>(null) }
    val selectedTime = remember { mutableStateOf<Long?>(null) }

    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange =
                {
                    checkedState.value = it
                    selectedTime.value = null
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = selectedColor.base,
                    uncheckedColor = selectedColor.base
                )
            )
            Text("Добавить дату самоуничтожения")
        }

        Button(onClick = {openDateDialog.value = true},
            colors = ButtonDefaults.buttonColors(
                    containerColor = selectedColor.base),
            enabled = checkedState.value){
            Text("Выбрать дату")
        }

        val selectedTimeValue = selectedTime.value

        if (selectedTimeValue != null) {
            val date = Date(selectedTimeValue)
            val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
            val formattedDate = dateFormat.format(date)

            Text("Выбранная дата: $formattedDate")
        } else if (checkedState.value) {Text("Дата не выбрана")}

        if (openDateDialog.value) {
            DatePickerModal(
                onDateSelected =
                {
                    millis -> selectedDate.value = millis
                    openTimeDialog.value = true
                },
                onDismiss = {openDateDialog.value = false}
            )
        }
        if (openTimeDialog.value) {
            TimePickerModal(
                selectedDateMillis  = selectedDate.value,
                onConfirm =
                {
                    timeInMillis -> selectedTime.value = timeInMillis
                },
                onDismiss = {openTimeDialog.value = false}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {

    val today = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    val datePickerState = rememberDatePickerState()
    val isDateValid = remember(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let { it >= today } ?: false
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (isDateValid) {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            },
                enabled = isDateValid
            ) {
                Text("OK", color = if (isDateValid) selectedColor.dark else Color(0x50000000))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = selectedColor.dark)
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = selectedColor.lightest
        )
    ) {
        DatePicker(state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = selectedColor.lightest,
                currentYearContentColor = selectedColor.dark,
                dayInSelectionRangeContainerColor = selectedColor.dark,
                dividerColor = selectedColor.dark,
                selectedDayContainerColor = selectedColor.dark,
                selectedYearContainerColor = selectedColor.dark,
                todayContentColor = selectedColor.dark,
                todayDateBorderColor = selectedColor.dark

            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    selectedDateMillis: Long?,
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val currentCalendar = remember { Calendar.getInstance() }

    val timePickerState = rememberTimePickerState(
        initialHour = currentCalendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentCalendar.get(Calendar.MINUTE),
        is24Hour = true,
    )

    fun isToday(dateMillis: Long?): Boolean {
        if (dateMillis == null) return false
        val todayCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val dateCal = Calendar.getInstance().apply {
            timeInMillis = dateMillis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return todayCal.timeInMillis == dateCal.timeInMillis
    }

    val minHour = if (isToday(selectedDateMillis)) currentCalendar.get(Calendar.HOUR_OF_DAY) else 0
    val minMinute = if (isToday(selectedDateMillis)) currentCalendar.get(Calendar.MINUTE) else 0

    val isTimeValid by remember(timePickerState.hour, timePickerState.minute, selectedDateMillis) {
        mutableStateOf(
            if (!isToday(selectedDateMillis)) true
            else {
                if (timePickerState.hour > minHour) true
                else if (timePickerState.hour == minHour && timePickerState.minute >= minMinute) true
                else false
            }
        )
    }

    TimePickerDialog(
        onConfirm = {
            if (isTimeValid) {
                val selectedTimeInMillis = Calendar.getInstance().apply {
                    timeInMillis = selectedDateMillis ?: System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                onConfirm(selectedTimeInMillis)
                onDismiss()
            }
        },
        onDismiss = { onDismiss() },
        confirmEnabled = isTimeValid
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
fun TimePickerDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }
            ) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() },
                enabled = confirmEnabled
            ) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

@Composable
fun NoteColor() {
    val openColorPickerDialog = remember {mutableStateOf(false)}
    val noteColors = listOf(gray, banana, lime, blueberry, raspberry, strawberry)
    val brush = Brush.sweepGradient(listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red))
    Column {
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
                                selectedColor = color
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
            onConfirm = {selectedColor = custom},
            onDismiss = {openColorPickerDialog.value = false}
        )
    }
}

@Composable
fun CustomColorPicker(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var pickedColor: Color? by remember { mutableStateOf(null)}
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
                                    Box(Modifier
                                        .size(width = 3.dp, height = 3.dp)
                                        .background(color)
                                        .selectable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(),
                                            selected = pickedColor == color,
                                            onClick = {
                                                pickedColor = color
                                                generateColorShades(color)
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
            }) {
                Text("Сохранить")
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

private object NoRippleTheme: RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        0.0f, 0.0f, 0.0f, 0.0f
    )
}

@Composable
fun NoteImportance() {
    val importance = listOf(Importance.UNIMPORTANT, Importance.NORMAL, Importance.IMPORTANT)
    val importanceName = mapOf(Pair(Importance.UNIMPORTANT, "Неважная"), Pair(Importance.NORMAL, "! Обычная !"), Pair(Importance.IMPORTANT, "!! Важная !!"))
    val selectedImportance = remember { mutableStateOf(Importance.NORMAL) }
    Column {
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

fun rgbToHsl(rf: Float, gf: Float, bf: Float): FloatArray {
    val max = maxOf(rf, gf, bf)
    val min = minOf(rf, gf, bf)
    val delta = max - min

    val l = (max + min) / 2f
    val s: Float
    val h: Float

    if (delta == 0f) {
        h = 0f
        s = 0f
    } else {
        s = if (l < 0.5f) delta / (max + min) else delta / (2f - max - min)
        h = when (max) {
            rf -> ((gf -bf) / delta + if (gf < bf) 6f else 0f) * 60f
            gf -> ((bf - rf) / delta + 2f) * 60f
            else -> ((rf - gf) / delta + 4f) * 60f
        }
    }
    return floatArrayOf(h % 360, s, l)
}

fun hslToRgb(h: Float, s: Float, l: Float): Color {
    val c = (1f - abs(2 * l - 1)) * s
    val x = c * (1f - abs((h / 60f) % 2 - 1))
    val m = l - c / 2f

    val (r1, g1, b1) = when {
        h < 60f -> Triple(c, x, 0f)
        h < 120f -> Triple(x, c, 0f)
        h < 180f -> Triple(0f, c, x)
        h < 240f -> Triple(0f, x, c)
        h < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    val r = ((r1 + m) * 255).toInt().coerceIn(0, 255)
    val g = ((g1 + m) * 255).toInt().coerceIn(0, 255)
    val b = ((b1 + m) * 255).toInt().coerceIn(0, 255)

    return Color(r, g, b)
}

fun generateColorShades(baseColor: Color) {
    val r = baseColor.red
    val g = baseColor.green
    val b = baseColor.blue

    val (h, s, l) = rgbToHsl(r, g, b)

    custom = NoteColorPalette(
        baseColor,
        hslToRgb(h, s, (l + 0.2f).coerceIn(0f, 1f)),
        hslToRgb(h, s, (l + 0.4f).coerceIn(0f, 1f)),
        hslToRgb(h, s, (l * 0.6f).coerceIn(0f, 1f)),
        hslToRgb(h, s, (l * 0.8f).coerceIn(0f, 1f))
        )
}

@Preview(showBackground = true)
@Composable
fun NoteEditScreenPreview() {
    NoteEditScreen()
}