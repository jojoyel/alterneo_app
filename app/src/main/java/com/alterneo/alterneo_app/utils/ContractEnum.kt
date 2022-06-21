package com.alterneo.alterneo_app.utils

import com.alterneo.alterneo_app.R

enum class ContractEnum(val id: Int, val string: Int) {
    CDD(1, R.string.contract_permanent),
    CDI(2, R.string.contract_fixed),
    CTT(3, R.string.contract_temp),
    CUI(4, R.string.contract_integ),
    CAE(5, R.string.contract_support),
    CIE(6, R.string.contract_initiative);

    companion object {
        fun findContractById(id: Int): ContractEnum? {
            return values().find { it.id == id }
        }
    }
}