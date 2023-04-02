package com.bas.acmerouting.shipment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bas.acmerouting.shipment.composables.ShipmentDetailUi
import com.bas.acmerouting.shipment.composables.ShipmentsListUi
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
                        composable("shipmentsList") { ShipmentsListUi(navController, shipments) }
                        composable("shipmentDetail/{driverName}/{destinationAddress}",
                            arguments = listOf(
                                navArgument("driverName") {type = NavType.StringType},
                                navArgument("destinationAddress") {type = NavType.StringType}
                            )
                        ) { backStackEntry ->
                            ShipmentDetailUi(
                                driverName = backStackEntry.arguments?.getString("driverName") ?: "Error",
                                destinationAddress = backStackEntry.arguments?.getString("destinationAddress") ?: "Nowhere"
                            )
                        }
                    }
                }
            }
        }
    }
}

