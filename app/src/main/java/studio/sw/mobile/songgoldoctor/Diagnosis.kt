package studio.sw.mobile.songgoldoctor

import android.provider.ContactsContract
import java.util.*

data class Diagnosis(
    val writer: String,
    val date: Date,
    val hospital: String,
    val department: Department,
    val doctor: DoctorProfile,
    val patient: Profile,
    val diseaseName: String,
    val diagnosisType: DiagnosisType,
    val onsetDate: Date,
    val diagnosisDate: Date,
    val futureMedicalOpinion: String)