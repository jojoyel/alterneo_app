package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alterneo.alterneo_app.feature_map.domain.model.Proposal
import com.alterneo.alterneo_app.utils.ContractEnum

@Composable
fun ProposalItem(proposal: Proposal, companyName: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = proposal.title ?: "Nom indisponible", style = MaterialTheme.typography.h5)
            Text(
                text = "${stringResource(id = ContractEnum.findContractById(proposal.id)?.string ?: ContractEnum.CDI.string)} / $companyName",
                color = MaterialTheme.typography.body1.color.copy(alpha = .5f)
            )
            if (proposal.contractType != 1) {
                Text(
                    text = buildString(proposal),
                    color = MaterialTheme.typography.body1.color.copy(alpha = .5f)
                )
            }
            if (proposal.required_skills?.isNotBlank() == true)
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
    proposal.durationYear?.let {
        if (it > 0) {
            val hasS = if (it > 1) "s" else ""
            s += "$it an$hasS "
        }
    }
    proposal.durationMonth?.let {
        if (it > 0)
            s += "$it mois "
    }
    proposal.durationWeek?.let {
        if (it > 0) {
            val hasS = if (it > 1) "s" else ""
            s += "$it semaine$hasS "
        }
    }
    proposal.durationDay?.let {
        if (it > 0) {
            val hasS = if (it > 1) "s" else ""
            s += "$it jour$hasS"
        }
    }
    return s
}