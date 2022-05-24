package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tv
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterneo.alterneo_app.feature_map.domain.model.Proposal

@Composable
fun ProposalItem(proposal: Proposal, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(16.dp).fillMaxWidth()) {
        Column() {
            Row() {
                Image(
                    Icons.Default.Tv,
                    contentDescription = "",
                    modifier =  Modifier.size(100.dp)

                )
                Column() {
                    Text(text = proposal.title , style = MaterialTheme.typography.h5)
                    Text(text = "${proposal.description}", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun ProposalItemPreview() {
    ProposalItem(
        Proposal(
            id = 1,
            title = "Title",
            description = "Description"
        )
    )
}