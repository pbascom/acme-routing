package com.bas.acmerouting.shipment.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bas.acmerouting.shipment.data.Destination
import com.bas.acmerouting.shipment.data.Driver
import com.bas.acmerouting.theme.AcmeRoutingTheme

@Composable
fun ShipmentsListUi(navController: NavController, shipments: Map<Driver, Destination>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
        )
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "This is the router screen"
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {
                // This should be items(), rather than itemsIndexed, but Android Studio is refusing
                // to recognize the right overload of the items method. Rather than spend another
                // hour figuring out why, I'm using a function which always takes a List. In a
                // production app, I'd invest the time, though.
                itemsIndexed(shipments.entries.toList()) { _, entry ->
                    ShipmentItem(driver = entry.key, destination = entry.value) {
                        navController.navigate("shipmentDetail/${entry.key.name}/${entry.value.address}")
                    }
                }
            }
        }
    }
}

@Composable
fun ShipmentItem(driver: Driver, destination: Destination, onClick: () -> Unit) {
    Text(
        modifier = Modifier.clickable(onClick = onClick),
        text = "${driver.name} should go to ${destination.address}"
    )
}

@Preview(
    widthDp = 270,
    heightDp = 585,
    showBackground = true
)
@Composable
fun ShipmentsListPreview() {
    AcmeRoutingTheme {
        ShipmentsListUi(
            rememberNavController(),
            hashMapOf(
                Driver("The Muffin Man") to Destination("123 Drury Lane"),
                Driver("Big Bird") to Destination("Sesame Street"),
                Driver("Harry Potter") to Destination("#12 Grimmauld Place"),
                Driver("Owl") to Destination("Piglet's Old Place")
            )
        )
    }
}