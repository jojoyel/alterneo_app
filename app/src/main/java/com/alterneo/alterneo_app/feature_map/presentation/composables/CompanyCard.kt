package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alterneo.alterneo_app.feature_map.domain.model.Company

@Composable
fun CompanyCard(company: Company, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        company.companyRegistration?.let {
            Row() {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = company.companyRegistration.name, style = MaterialTheme.typography.h2)
                    Spacer(modifier = Modifier.height(8.dp))
//                    Text(text = company., style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}