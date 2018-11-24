package studio.sw.mobile.songgoldoctor

import java.util.*

data class Profile(
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var birthDate: Date
)

data class DoctorProfile(
    var firstName: String,
    var lastName: String,
    var gender: Gender,
    var birthDate: Date,
    var hospital: String,
    var specialization: Department
)