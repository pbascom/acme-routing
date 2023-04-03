package com.bas.acmerouting.shipment.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bas.acmerouting.R
import com.bas.acmerouting.theme.AcmeRoutingTheme

@Composable
fun ShipmentDetailUi(driverName: String, destinationAddress: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.shipment_details_page_title)) },
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                color = MaterialTheme.colors.primarySurface
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.caption,
                    text = "Shipment information for $driverName"
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = "Destination: $destinationAddress."
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
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