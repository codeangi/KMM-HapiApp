package com.deepak.myapplication.infra

//private const val HAPI_BASE_URL = "https://hapi.fhir.org/baseR4/"
private const val YCARE_BASE_URL = "https://ycares-dev-hapi.ymedia.in/fhir/"
private const val YML_DEV_SERVER = "https://ycares-dev-backend.ymedia.in/"
const val BASE_URL = YCARE_BASE_URL

object RemoteRoutes {

    fun GET_PATIENT_DETAILS(patientId: String): String {
        return BASE_URL + "Patient/$patientId"
    }
    const val GET_PATIENT_CARE_TEAM = BASE_URL + "CareTeam"

    const val ACCESS_TOKEN = "${YML_DEV_SERVER}token"
    const val APPOINTMENT = "${YML_DEV_SERVER}fhir/appointments"
    fun GET_PRACTITIONER_DETAILS(practitionerId: String): String {
        return "${YCARE_BASE_URL}Practitioner/$practitionerId"
    }

    const val SLOT = "${YML_DEV_SERVER}fhir/appointments/\$find"

    object Parameters {
        const val Participant = "participant"
    }
}