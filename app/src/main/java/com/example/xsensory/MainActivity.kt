// MainActivity.kt
// UI is in MainUI.kt
package com.example.xsensory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.xsensory.ui.theme.XSensory_CanaryTheme

class MainActivity : ComponentActivity() {
    private var messageText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XSensory_CanaryTheme {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    MessageCard()
                    SendButton {

                    }
                    FilesButton {

                    }
                    LoggerCard()
                }
            }
        }
    }
}