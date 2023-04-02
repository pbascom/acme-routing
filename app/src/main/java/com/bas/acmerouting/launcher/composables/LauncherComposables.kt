package com.bas.acmerouting.launcher.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bas.acmerouting.theme.AcmeRoutingTheme

@Composable
fun LauncherUi(
    onRouteShipmentsButtonClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Acme Routing")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onRouteShipmentsButtonClicked
                ) {
                    Text("Route Shipments")
                }
            }
        }
    }
}

// Same aspect ratio as a Pixel 5
@Preview(
    widthDp = 270,
    heightDp = 585,
    showBackground = true
)
@Composable
fun LauncherPreview() {
    AcmeRoutingTheme {
        LauncherUi({})
    }
}