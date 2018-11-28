package studio.sw.mobile.songgoldoctor

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import java.util.*

class Diagnosis(
    val writer: String,
    val date: Date,
    val hospital: String,
    val department: Department,

    val medicine: String,
    val diseaseName: String,
    val diagnosisType: DiagnosisType,
    val onsetDate: Date,
    val diagnosisDate: Date,
    val futureMedicalOpinion: String,
    val howMany: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readSerializable() as Date,
        source.readString(),
        Department.values()[source.readInt()],
        source.readString(),
        source.readString(),
        DiagnosisType.values()[source.readInt()],
        source.readSerializable() as Date,
        source.readSerializable() as Date,
        source.readString(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(writer)
        writeSerializable(date)
        writeString(hospital)
        writeInt(department.ordinal)
        writeString(medicine)
        writeString(diseaseName)
        writeInt(diagnosisType.ordinal)
        writeSerializable(onsetDate)
        writeSerializable(diagnosisDate)
        writeString(futureMedicalOpinion)
        writeInt(howMany)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Diagnosis> = object : Parcelable.Creator<Diagnosis> {
            override fun createFromParcel(source: Parcel): Diagnosis = Diagnosis(source)
            override fun newArray(size: Int): Array<Diagnosis?> = arrayOfNulls(size)
        }
    }
}

