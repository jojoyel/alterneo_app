package com.alterneo.alterneo_app.utils

import com.alterneo.alterneo_app.R

enum class BusinessSectorEnum(val id: Int, val string: Int) {
    AGRICULTURE(1, R.string.busi_agriculture),
    EXTRACTIVE(2, R.string.busi_extractive),
    MANUFACTURING(3, R.string.busi_manufacturing),
    ELECTRICITY(4, R.string.busi_electricity),
    WATER(5, R.string.busi_water),
    WASTE(6, R.string.busi_waste),
    CONSTRUCTION(7, R.string.busi_construction),
    AUTOMOTO(8, R.string.busi_automoto),
    TRANSPORT(9, R.string.busi_transport),
    ACCOMMODATION(10, R.string.busi_accomodation),
    INFORMATION(11, R.string.busi_information),
    FINANCIAL(12, R.string.busi_financial),
    REALESTATE(13, R.string.busi_realestate),
    SCIENTIFIC(14, R.string.busi_scientific),
    ADMINSERVICE(15, R.string.busi_adminservices),
    PUBLICADMIN(16, R.string.busi_publicadmin),
    EDUCATION(17, R.string.busi_education),
    HEALTH(18, R.string.busi_health),
    ARTS(19, R.string.busi_arts),
    OTHER(20, R.string.busi_other),
    EXTRATERRITORIAL(21, R.string.busi_extraterritorial),
    HOUSEHOLDS(22, R.string.busi_households);

    companion object {
        fun fromId(id: Int): BusinessSectorEnum? = enumValues<BusinessSectorEnum>().firstOrNull { it.id == id }
    }
}

