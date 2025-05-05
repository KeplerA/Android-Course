package com.example.helloapp.model

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color

data class NoteColorPalette(val base: Color, val light: Color, val lightest: Color, val dark: Color, val darkest: Color)

@SuppressLint("ResourceAsColor")
val gray: NoteColorPalette = NoteColorPalette(
    Color(0xFFC2C2C2),
    Color(0xFFFAFAFA),
    Color(0xFFEFEFEF),
    Color(0xFFAFAFAF),
    Color(0xFF939393)
)

@SuppressLint("ResourceAsColor")
val nut: NoteColorPalette = NoteColorPalette(
    Color(0xFFD9731B),
    Color(0xFFF7B175),
    Color(0xFFF7D7BB),
    Color(0xFF835E3E),
    Color(0xFF612E02)
)

@SuppressLint("ResourceAsColor")
val banana: NoteColorPalette = NoteColorPalette(
    Color(0xFFD9A91B),
    Color(0xFFF7D775),
    Color(0xFFF7E8BB),
    Color(0xFF83723E),
    Color(0xFF614902)
)

@SuppressLint("ResourceAsColor")
val lime: NoteColorPalette = NoteColorPalette(
    Color(0xFF83B25C),
    Color(0xFFBBE09C),
    Color(0xFFCEE2BE),
    Color(0xFF537537),
    Color(0xFF285602)
)

@SuppressLint("ResourceAsColor")
val blueberry: NoteColorPalette = NoteColorPalette(
    Color(0xFF425B94),
    Color(0xFF839CD5),
    Color(0xFFB6C5E9),
    Color(0xFF2E3B58),
    Color(0xFF031641)
)

@SuppressLint("ResourceAsColor")
val grape: NoteColorPalette = NoteColorPalette(
    Color(0xFF501D93),
    Color(0xFFA979E9),
    Color(0xFFCCB5E9),
    Color(0xFF402E59),
    Color(0xFF1E0342)
)

@SuppressLint("ResourceAsColor")
val raspberry: NoteColorPalette = NoteColorPalette(
    Color(0xFFAF4782),
    Color(0xFFE083B7),
    Color(0xFFEFB4D5),
    Color(0xFF693251),
    Color(0xFF4D022D)
)

@SuppressLint("ResourceAsColor")
val strawberry: NoteColorPalette = NoteColorPalette(
    Color(0xFFDD5858),
    Color(0xFFED8181),
    Color(0xFFF7BBBB),
    Color(0xFF833E3E),
    Color(0xFF610202)
)