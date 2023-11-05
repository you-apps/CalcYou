package net.youapps.calcyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import net.youapps.calcyou.ui.CalculatorScreen
import net.youapps.calcyou.ui.theme.CalcYouTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcYouTheme {
                // A surface container using the 'background' color from the theme
                CalculatorScreen()
            }
        }
    }
}