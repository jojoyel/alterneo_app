package com.alterneo.alterneo_app.responses

import com.alterneo.alterneo_app.models.Proposal

class ProposalsResponse(
    var total_returned: Number,
    var data: List<ProposalResponse>
) : ArrayResponse<ProposalResponse>(total_returned, data) {
    fun toProposals(): List<Proposal> {
        val toReturn = ArrayList<Proposal>()
        data.forEach { toReturn.add(it.toProposal()) }
        return toReturn
    }
}