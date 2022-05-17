package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alterneo.alterneo_app.feature_map.domain.model.Proposal

@Composable
fun ProposalItem(proposal: Proposal, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(16.dp)) {
        Column(modifier = Modifier) {
            Row(modifier = Modifier) {
                Image(Icons.Default.Tv, contentDescription = "")
                Column() {
                    Text(text = proposal.title)
//                    Text(text = )
                    
                }
            }
        }
    }
}