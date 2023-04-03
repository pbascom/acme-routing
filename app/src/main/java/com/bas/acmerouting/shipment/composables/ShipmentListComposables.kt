package com.bas.acmerouting.shipment.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bas.acmerouting.R
import com.bas.acmerouting.shipment.data.Destination
import com.bas.acmerouting.shipment.data.Driver
import com.bas.acmerouting.theme.AcmeRoutingTheme

@Composable
fun ShipmentsListUi(navController: NavController, shipments: Map<Driver, Destination>) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.shipment_list_page_title)) },
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
        )
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.shipment_list_page_explanation)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        color = MaterialTheme.colors.primarySurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MaterialTheme.typography.caption,
                text = driver.name
            )
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "")
        }

    }

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