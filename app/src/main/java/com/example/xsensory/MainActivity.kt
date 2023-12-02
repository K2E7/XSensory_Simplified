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
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.AdvertisingOptions
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo
import com.google.android.gms.nearby.connection.DiscoveryOptions
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback
import com.google.android.gms.nearby.connection.Strategy
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MainActivity : ComponentActivity() {

    private val SERVICE_ID = "XSensory-TestRelease"
    private lateinit var connectionsClient : ConnectionsClient
    private var messageText: String = ""

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            // Handle the connection initiation from the remote device
            // You can accept or reject the connection here
        }

        override fun onConnectionResult(endpointId: String, resolution: ConnectionResolution) {
            // Handle the connection result, whether it's successful or not
        }

        override fun onDisconnected(endpointId: String) {
            // Handle the disconnection of the remote device
        }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            // Handle the discovery of a nearby endpoint
        }

        override fun onEndpointLost(endpointId: String) {
            // Handle the loss of a nearby endpoint
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Nearby Connections
        connectionsClient = Nearby.getConnectionsClient(this)

        startAdvertising()
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
                        startDiscovery()
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

    private fun startAdvertising() {
        val advertisingOptions = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()

        connectionsClient.startAdvertising(
            /* p0 = */ "MyDevice", // Set a name for your device
            /* p1 = */ SERVICE_ID,
            /* p2 = */ connectionLifecycleCallback,
            /* p3 = */ advertisingOptions
        )
            .addOnSuccessListener {
                // Advertising started successfully
                Toast.makeText(this, "Advertising started", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // Advertising failed
                Toast.makeText(this, "Advertising failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun startDiscovery() {
        // Discovery options (use the same strategy as in advertising)
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()

        // Start discovery
        connectionsClient.startDiscovery(
            /* p0 = */ SERVICE_ID,
            /* p1 = */ endpointDiscoveryCallback,
            /* p2 = */ discoveryOptions
        )
            .addOnSuccessListener {
                // Discovery started successfully
                Toast.makeText(this, "Discovery started", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // Discovery failed
                Toast.makeText(this, "Discovery failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}