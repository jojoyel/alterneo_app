package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alterneo.alterneo_app.feature_map.domain.model.Proposal

@Composable
fun ProposalItem(proposal: Proposal, companyName: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = proposal.title, style = MaterialTheme.typography.h5)
            Text(text = "${proposal.contractType} / $companyName")
            if (proposal.contractType != 1) {
                Text(text = buildString(proposal))
            }
            if (proposal.required_skills.isNotBlank())
                Text(
                    text = proposal.required_skills,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
        }
    }
}

fun buildString(proposal: Proposal): String {
    var s = ""
    if (proposal.durationYear > 0) {
        val hasS = if (proposal.durationYear > 1) "s" else ""
        s += "${proposal.durationYear} an$hasS "
    }
    if (proposal.durationMonth > 0) {
        s += "${proposal.durationMonth} mois "
    }
    if (proposal.durationWeek > 0) {
        val hasS = if (proposal.durationWeek > 1) "s" else ""
        s += "${proposal.durationWeek} semaine$hasS "
    }
    if (proposal.durationDay > 0) {
        val hasS = if (proposal.durationDay > 1) "s" else ""
        s += "${proposal.durationDay} jour$hasS "
    }
    return s
}