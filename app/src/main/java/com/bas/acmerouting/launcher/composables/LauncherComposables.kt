package com.bas.acmerouting.launcher.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bas.acmerouting.R
import com.bas.acmerouting.theme.AcmeRoutingTheme

@Composable
fun LauncherUi(
    onRouteShipmentsButtonClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                navigationIcon = { Icon(imageVector = Icons.Filled.Menu, contentDescription = "") },
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                style = MaterialTheme.typography.h5,
                text = stringResource(id = R.string.launcher_page_body)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onRouteShipmentsButtonClicked
                ) {
                    Text(stringResource(id = R.string.launcher_page_button_text))
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