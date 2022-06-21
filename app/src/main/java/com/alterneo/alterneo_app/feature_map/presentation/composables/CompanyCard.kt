package com.alterneo.alterneo_app.feature_map.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.alterneo.alterneo_app.feature_map.domain.model.Company
import com.alterneo.alterneo_app.feature_map.presentation.MapScreenViewModel
import com.alterneo.alterneo_app.utils.BusinessSectorEnum

@Composable
fun CompanyCard(company: Company, modifier: Modifier = Modifier, viewModel: MapScreenViewModel) {
    company.companyRegistration?.let {
        Column() {
            Row(modifier = modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier.size(75.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = MaterialTheme.colors.onBackground.copy(alpha = .2f)
                ) {
                    SubcomposeAsyncImage(
                        model = it.image,
                        loading = {
                            CircularProgressIndicator()
                        },
                        contentDescription = "${it.name}'s image",
                        contentScale = ContentScale.Crop
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                    Text(
                        text = company.companyRegistration.name,
                        style = MaterialTheme.typography.h5
                    )
                    BusinessSectorEnum.fromId(company.companyRegistration.businessSectorId)?.let {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = "Storefront"
                            )
                            Text(
                                text = stringResource(id = it.string),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.typography.body1.color.copy(alpha = .5f)
                            )
                        }
                    }
                    Row {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "Employees number"
                        )
                        Text(
                            text = company.companyRegistration.employeesNumber.toString(),
                            color = MaterialTheme.typography.body1.color.copy(alpha = .5f)
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                company.proposals?.let {
                    items(it) { proposal ->
                        ProposalItem(proposal = proposal, company.companyRegistration.name)
                    }
                }
            }
            if (viewModel.state.selectedCompanyProposalsLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier
                        .width(48.dp)
                        .height(48.dp))
                }
            }
        }
    } ?: run {
        Text(text = "Problème de récupération de l'entreprise")
    }
}