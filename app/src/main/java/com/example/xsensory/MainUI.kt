// MainUI.kt

package com.example.xsensory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xsensory.ui.theme.XSensory_CanaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageCard() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(20.dp)
            .size(width = 480.dp, height = 200.dp)
            .clip(shape = RoundedCornerShape(12.dp))
    )
}

@Composable
fun SendButton(onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(width = 480.dp, height = 50.dp)
    ) {
        Text("SEND", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun FilesButton(onClick: () -> Unit) {
    ElevatedButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(width = 480.dp, height = 50.dp)
    ) {
        Text("CHOOSE FILE", fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}

@Composable
fun LoggerCard() {
    ElevatedCard(
        modifier = Modifier
            .size(480.dp),
        colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(
            text = "",
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    XSensory_CanaryTheme {
        // A surface container using the 'background' color from the theme
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MessageCard()
            SendButton {}
            FilesButton{}
            LoggerCard()
        }
    }
}