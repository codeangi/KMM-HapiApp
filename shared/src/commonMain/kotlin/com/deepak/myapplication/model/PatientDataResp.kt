package com.deepak.myapplication.model

/**
 * {
"resourceType": "Patient",
"id": "7003297",
"meta": {
"versionId": "1",
"lastUpdated": "2022-09-14T09:17:47.667+00:00",
"source": "#3MM9tG4zjEeKRlBA"
},
"text": {
"status": "generated",
"div": "<div xmlns=\"http://www.w3.org/1999/xhtml\"><div class=\"hapiHeaderText\">Kathleen <b>BONO </b></div><table class=\"hapiPropertyTable\"><tbody><tr><td>Identifier</td><td>924739</td></tr></tbody></table></div>"
},
"identifier": [
{
"system": "http://snomed.info/sct",
"value": "924739"
}
],
"name": [
{
"use": "maiden",
"family": "Bono",
"given": [
"Kathleen"
]
}
]
}
 */
@kotlinx.serialization.Serializable
data class PatientDataResp(
    val resourceType:String,
    val id:String,
    val name: List<Name>?
)