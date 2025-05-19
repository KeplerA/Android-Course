package com.example.helloapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.helloapp.model.NoteColorPalette
import com.example.helloapp.model.gray
import com.example.helloapp.ui.theme.HelloAppTheme
import org.slf4j.LoggerFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloAppTheme {
                Scaffold {
                    var selectedColor by remember { mutableStateOf(gray) }

                    var custom by remember { mutableStateOf(
                        NoteColorPalette(
                            Color.Unspecified,
                            Color.Unspecified,
                            Color.Unspecified,
                            Color.Unspecified,
                            Color.Unspecified
                        )
                    )}

                    CompositionLocalProvider(
                        LocalRippleTheme provides NoRippleTheme,
                        LocalSelectedColor provides selectedColor,
                        LocalCustomColor provides custom) {
//                        NoteEditScreen(
//                            Modifier
//                                .background(selectedColor.light)
//                                .padding(it))
                        Column (Modifier
                                .background(selectedColor.light)
                                .padding(it)
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            NoteContent()
                            NoteDestructionDate()
                            NoteColor(
                                selectedColor = selectedColor,
                                onSelectedColorChange = {selectedColor = it},
                                custom = custom,
                                onCustomChange = {custom = it}
                            )
                            NoteImportance()
                        }
                        val log = LoggerFactory.getLogger(MainActivity::class.java)
                        log.info("logger operation check")
                    }
                }
            }
        }
    }
}

val LocalSelectedColor = compositionLocalOf<NoteColorPalette> {
    error("No SelectedColor provided")
}

val LocalCustomColor = compositionLocalOf<NoteColorPalette> {
    error("No CustomColor provided")
}

//@Composable
//fun NoteEditScreen(modifier: Modifier = Modifier) {
//    Column (modifier = modifier.then(
//        Modifier
//            .fillMaxSize()
//            .padding(10.dp)),
//        verticalArrangement = Arrangement.spacedBy(10.dp)) {
//        NoteContent(selectedColor)
//        NoteDestructionDate(selectedColor)
//        NoteColor(
//            selectedColor = selectedColor,
//            onSelectedColorChange = {selectedColor = it},
//            custom = custom,
//            onCustomChange = {custom = it}
//        )
//        NoteImportance(selectedColor)
//    }
//}

private object NoRippleTheme: RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        0.0f, 0.0f, 0.0f, 0.0f
    )
}

//@Preview(showBackground = true)
//@Composable
//fun NoteEditScreenPreview() {
//    NoteEditScreen()
//}