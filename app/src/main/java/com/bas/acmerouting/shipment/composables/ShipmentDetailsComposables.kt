package com.bas.acmerouting.shipment.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bas.acmerouting.theme.AcmeRoutingTheme

@Composable
fun ShipmentDetailUi(driverName: String, destinationAddress: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        )
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Hello ${driverName}!"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "You should go to ${destinationAddress}."
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Have fun!"
            )
        }
    }
}

@Preview(
    widthDp = 270,
    heightDp = 585,
    showBackground = true
)
@Composable
fun ShipmentDetailPreview() {
    AcmeRoutingTheme {
        ShipmentDetailUi(
            driverName= "The Muffin Man",
            destinationAddress = "123 Drury Lane"
        )
    }
}