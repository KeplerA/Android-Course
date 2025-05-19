package com.example.helloapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NoteDestructionDate() {
    val checkedState = remember { mutableStateOf(false) }
    val openDateDialog = remember { mutableStateOf(false) }
    val openTimeDialog = remember { mutableStateOf(false) }
    val selectedDate = remember { mutableStateOf<Long?>(null) }
    val selectedTime = remember { mutableStateOf<Long?>(null) }
    val selectedColor = LocalSelectedColor.current

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
        } else if (checkedState.value) {
            Text("Дата не выбрана")
        }

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
    val selectedColor = LocalSelectedColor.current

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
                Text("ОК", color = if (isDateValid) selectedColor.dark else Color(0x50000000))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отменить", color = selectedColor.dark)
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
                Text("Отменить")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() },
                enabled = confirmEnabled
            ) {
                Text("ОК")
            }
        },
        text = { content() }
    )
}