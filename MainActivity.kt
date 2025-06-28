package com.example.wifiscanner

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var btnScanNetworks: MaterialButton
    private lateinit var rvWifiNetworks: RecyclerView
    private lateinit var tvNoNetworks: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        btnScanNetworks = findViewById(R.id.btn_scan_networks)
        rvWifiNetworks = findViewById(R.id.rv_wifi_networks)
        tvNoNetworks = findViewById(R.id.tv_no_networks)

        // Setup RecyclerView
        rvWifiNetworks.layoutManager = LinearLayoutManager(this)
        // TODO: Set up WiFi network adapter when implemented

        // Set up scan button click listener
        btnScanNetworks.setOnClickListener {
            // TODO: Implement WiFi scanning logic
            updateNetworksList()
        }
    }

    private fun updateNetworksList() {
        // TODO: Update networks list based on scan results
        // For now, just toggle visibility of no networks placeholder
        tvNoNetworks.visibility = if (rvWifiNetworks.adapter?.itemCount == 0) View.VISIBLE else View.GONE
    }
}