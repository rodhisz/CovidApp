package com.example.covidapp.model

import com.google.gson.annotations.SerializedName

data class ResponseCovid(

	@field:SerializedName("ResponseCovid")
	val responseCovid: List<ResponseCovidItem?>? = null
)

data class ResponseCovidItem(

	@field:SerializedName("meninggal")
	val meninggal: String? = null,

	@field:SerializedName("positif")
	val positif: String? = null,

	@field:SerializedName("sembuh")
	val sembuh: String? = null,

	@field:SerializedName("dirawat")
	val dirawat: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
