package com.bas.acmerouting.shipment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bas.acmerouting.shipment.composables.ShipmentDetailUi
import com.bas.acmerouting.shipment.data.Driver
import com.bas.acmerouting.shipment.data.Route
import com.bas.acmerouting.shipment.viewmodel.ShipmentsViewModel
import com.bas.acmerouting.theme.AcmeRoutingTheme

/**
 * This Activity represents the UI for the Routing feature, and is maintained by the Routing team.
 * See [com.bas.acmerouting.launcher.LauncherActivity] for an explanation.
 */
class ShipmentActivity : ComponentActivity() {
    private val viewModel: ShipmentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.refreshShipments()

        viewModel.shipmentsData.observe(this) { shipments ->
            setContent {
                AcmeRoutingTheme {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "shipmentsList" ) {
                        composable("shipmentsList") { ShipmentsListUi(navController, shipments)}
                        composable("shipmentDetail/{driverName}/{routeDestination}",
                            arguments = listOf(
                                navArgument("driverName") {type = NavType.StringType},
                                navArgument("routeDestination") {type = NavType.StringType}
                            )
                        ) { backStackEntry ->
                            ShipmentDetailUi(
                                driverName = backStackEntry.arguments?.getString("driverName") ?: "Error",
                                routeDestination = backStackEntry.arguments?.getString("routeDestination") ?: "Nowhere"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShipmentsListUi(navController: NavController, shipments: Map<Driver, Route>) {
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
                    ShipmentItem(driver = entry.key, route = entry.value) {
                        navController.navigate("shipmentDetail/${entry.key.name}/${entry.value.destination}")
                    }
                }
            }
        }
    }
}

@Composable
fun ShipmentItem(driver: Driver, route: Route, onClick: () -> Unit) {
    Text(
        modifier = Modifier.clickable(onClick = onClick),
        text ="${driver.name} should go to ${route.destination}"
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
                Driver("The Muffin Man") to Route("123 Drury Lane"),
                Driver("Big Bird") to Route("Sesame Street"),
                Driver("Harry Potter") to Route("#12 Grimmauld Place"),
                Driver("Owl") to Route("Piglet's Old Place")
            )
        )
    }
}