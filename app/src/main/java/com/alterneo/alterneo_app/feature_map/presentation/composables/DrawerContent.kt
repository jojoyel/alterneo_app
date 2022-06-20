package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alterneo.alterneo_app.feature_map.presentation.DrawerEvent

@Composable
fun DrawerContent(modifier: Modifier = Modifier, listener: (DrawerEvent) -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        IconButton(onClick = { listener(DrawerEvent.CloseClicked) }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Fermer")
        }
        Row(
            Modifier
                .clickable { listener(DrawerEvent.LogoutClicked) }
                .padding(8.dp)
                .fillMaxWidth()) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = "Déconnecter")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Se déconnecter")
        }
    }
}