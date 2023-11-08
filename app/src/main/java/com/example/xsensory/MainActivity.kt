// MainActivity.kt
// UI is in MainUI.kt
package com.example.xsensory

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
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
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException

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
                    MessageCard{
                        text ->
                        messageText = text
                    }
                    SendButton {
                        createTextFileInSubfolder("XSensory")
                    }
                    FilesButton {

                    }
                    LoggerCard()
                }
            }
        }
    }

    private fun createTextFileInSubfolder(subfolderName: String) {
        val fileName = "user_message.txt"

        // Check if external storage is available for writing
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Toast.makeText(this, "External storage unavailable", Toast.LENGTH_SHORT).show()
            return
        }

        val downloadsDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val subfolder = File(downloadsDirectory, subfolderName)

        try {
            // Create the subfolder if it doesn't exist
            if (!subfolder.exists()) {
                subfolder.mkdir()
            }

            // Create the file and write the message using a buffered writer
            val file = File(subfolder, fileName)
            val bufferedWriter = BufferedWriter(FileWriter(file))
            bufferedWriter.write(messageText)
            bufferedWriter.close()
            Toast.makeText(this, "Text file created in $subfolderName folder", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to create text file: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}