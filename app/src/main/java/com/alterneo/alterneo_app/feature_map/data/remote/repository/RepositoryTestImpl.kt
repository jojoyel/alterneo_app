package com.alterneo.alterneo_app.feature_map.data.remote.repository

import com.alterneo.alterneo_app.feature_map.data.remote.dto.*
import com.alterneo.alterneo_app.feature_map.domain.repository.Repository

//class RepositoryTestImpl constructor() : Repository {
//    override suspend fun getCompanies(page: Int): ArrayDto<CompanyRegistrationDto> {
//        return ArrayDto(
//            1,
//            listOf(
//                CompanyRegistrationDto(
//                    "10-10-2022",
//                    "5 allée des aulnes",
//                    1,
//                    "Mons-en-Laonnois",
//                    "09-10-2022",
//                    "Superbe entreprise",
//                    19,
//                    1,
//                    "https://play-lh.googleusercontent.com/IeNJWoKYx1waOhfWF6TiuSiWBLfqLb18lmZYXSgsH1fvb8v1IYiZr5aYWe0Gxu-pVZX3=w240-h480-rw",
//                    1,
//                    "jolalle974@gmail.com",
//                    "Entreprise",
//                    "02000",
//                    "13453582345834",
//                    "jsp",
//                    "+33783762076",
//                    "https://nextfor.fr",
//                    true
//                )
//            )
//        )
//    }
//
//    override suspend fun getCompany(id: Int): CompanyRegistrationDto {
//        return CompanyRegistrationDto(
//            "10-10-2022",
//            "5 allée des aulnes",
//            1,
//            "Mons-en-Laonnois",
//            "09-10-2022",
//            "Superbe entreprise",
//            19,
//            1,
//            "https://play-lh.googleusercontent.com/IeNJWoKYx1waOhfWF6TiuSiWBLfqLb18lmZYXSgsH1fvb8v1IYiZr5aYWe0Gxu-pVZX3=w240-h480-rw",
//            1,
//            "jolalle974@gmail.com",
//            "Entreprise",
//            "02000",
//            "13453582345834",
//            "jsp",
//            "+33783762076",
//            "https://nextfor.fr",
//            true
//        )
//    }
//
//    override suspend fun getCompaniesLocations(page: Int): ArrayDto<CompanyDto> {
//        return ArrayDto(
//            1, listOf(
//                CompanyDto(
//                    true,
//                    1,
//                    1,
//                    "https://media-exp1.licdn.com/dms/image/C4E0BAQHikN6EXPd23Q/company-logo_200_200/0/1595359131127?e=2147483647&v=beta&t=5V_-d8vUfunqRbLI1NoSUQrJtEOhDDWxOHBPqYo-7c0",
//                    "49.26335094209739",
//                    "4.035162350762649",
//                    companyRegistration = CompanyRegistrationDto(
//                        "10-10-2022",
//                        "5 allée des aulnes",
//                        1,
//                        "Mons-en-Laonnois",
//                        "09-10-2022",
//                        "Superbe entreprise",
//                        19,
//                        1,
//                        "https://play-lh.googleusercontent.com/IeNJWoKYx1waOhfWF6TiuSiWBLfqLb18lmZYXSgsH1fvb8v1IYiZr5aYWe0Gxu-pVZX3=w240-h480-rw",
//                        1,
//                        "jolalle974@gmail.com",
//                        "Entreprise",
//                        "02000",
//                        "13453582345834",
//                        "jsp",
//                        "+33783762076",
//                        "https://nextfor.fr",
//                        true
//                    )
//                )
//            )
//        )
//    }
//
//    override suspend fun getCompanyLocation(id: Int): CompanyDto {
//        return CompanyDto(
//            true,
//            1,
//            1,
//            "https://media-exp1.licdn.com/dms/image/C4E0BAQHikN6EXPd23Q/company-logo_200_200/0/1595359131127?e=2147483647&v=beta&t=5V_-d8vUfunqRbLI1NoSUQrJtEOhDDWxOHBPqYo-7c0",
//            "49.26335094209739",
//            "4.035162350762649",
//            companyRegistration = CompanyRegistrationDto(
//                "10-10-2022",
//                "5 allée des aulnes",
//                1,
//                "Mons-en-Laonnois",
//                "09-10-2022",
//                "Superbe entreprise",
//                19,
//                1,
//                "https://play-lh.googleusercontent.com/IeNJWoKYx1waOhfWF6TiuSiWBLfqLb18lmZYXSgsH1fvb8v1IYiZr5aYWe0Gxu-pVZX3=w240-h480-rw",
//                1,
//                "jolalle974@gmail.com",
//                "Entreprise",
//                "02000",
//                "13453582345834",
//                "jsp",
//                "+33783762076",
//                "https://nextfor.fr",
//                true
//            )
//        )
//    }
//
//    override suspend fun getCompanyProposals(companyId: Int): ArrayDto<ProposalDto> {
//        return ArrayDto(
//            1, listOf(
//                ProposalDto(
//                    1,
//                    20,
//                    "10-10-2022",
//                    1,
//                    "CDI",
//                    1653974439,
//                    "Superbe job",
//                    10,
//                    0,
//                    0,
//                    0,
//                    0,
//                    1,
//                    "Java, Kotlin",
//                    "H/F Dev android",
//                    "https://nextfor.fr"
//                )
//            )
//        )
//    }
//
//    override suspend fun getCompanyProposalsCount(companyId: Int): CountDto {
//        return CountDto(1)
//    }
//}