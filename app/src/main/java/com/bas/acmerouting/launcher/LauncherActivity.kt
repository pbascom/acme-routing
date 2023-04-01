package com.bas.acmerouting.launcher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bas.acmerouting.shipment.ShipmentActivity
import com.bas.acmerouting.theme.AcmeRoutingTheme

/**
 * Acme Routing is built to use a Compose UI. The Compose Navigation component lets us separate UI
 * boundaries from Activity boundaries, so we don't need to have a one-to-one correlation between
 * our Activities (or even our Fragments) and our screens.
 *
 * Instead, we'll be using Activities to represent logical or architectural boundaries. We'll draw
 * those boundaries wherever makes the most sense to maximize ease of development.
 *
 * Practically speaking, that means we're building Conway's Law into the structure of our application.
 * Each team can maintain their own code in their own directory; for UI-focused teams, that means
 * managing each of their UI flows as its own Activity, with its own associated namespace, ViewModel,
 * and Composables. Each team can develop their component(s) independently, using a wrapper Application
 * which only builds their own particular component; then, a core Foundations team can use Git
 * submodules (or a jFrog Artifactory) to pull components together into a single application when
 * it's time to bundle for release. The Foundations team will also be responsible for maintaining
 * a package of core functionality, UI elements, etc, which all other teams can import and depend on.
 *
 * This has two primary benefits. First, it lets teams be more autonomous; each team has a limited
 * ability to depend on other teams, and so they have ability to interfere with each others' work.
 * Each team can apply its own agreed-on best practices without worrying about how other teams are
 * doing it.
 *
 * Second, as the project scales, this modular approach keeps build times low for feature teams. I've
 * been on projects where it takes upwards of forty minutes to do a clean build of the application,
 * and even after throwing the most expensive hardware we could find at the problem, it never got
 * below five or so minutes per build. That kind of lag time is incredibly demoralizing and kills your
 * creativity; it also makes the red-green-refactor cycle of TDD completely impossible.
 *
 * This MainActivity represents the Launcher Activity maintained by the Foundations team. Its primary
 * purpose is to route users towards feature flows as necessary. (In a mature application, this may
 * be the login page or the landing page.)
 */
class LauncherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcmeRoutingTheme {
                LauncherUi(
                    onRouteShipmentsButtonClicked = ::launchRouterActivity
                )
            }
        }
    }

    private fun launchRouterActivity() {
        startActivity(
            Intent(this, ShipmentActivity::class.java)
        )
    }

}

@Composable
private fun LauncherUi(
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
                    Text ("Route Shipments")
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