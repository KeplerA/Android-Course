package com.example.helloapp

import androidx.compose.ui.graphics.Color
import com.example.helloapp.model.NoteColorPalette
import kotlin.math.abs

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