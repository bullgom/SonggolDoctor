package studio.sw.mobile.songgoldoctor

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

enum class Department { AnE, Anaesthetics, Cardiology,
    Chaplaincy, CriticalCare, DiagnosticImaging, ENT,
    Gastroenterology, GeneralSurgery, Gynaecology, Haematology,
    Microbiology, Nephrology, Neurology
}

enum class DiagnosisType { Estimation, Final }

enum class Gender { Male, Female }

enum class Week { Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Holiday }
@Parcelize
class Time(var hour:Int, var minute:Int):Comparable<Time>,Parcelable{
    fun equals(other: Time):Boolean{
        return (hour == other.hour && minute == other.minute)
    }
    operator fun plus(other: Time): Time {
        var nHour = hour + other.hour
        var nMinute = minute + other.minute
        when{
            nHour >= 24 -> nHour = 0
            nHour < 0 -> nHour = 0
        }
        when{
            nMinute >= 60 -> {
                nHour += 1
                nMinute = 0
            }
            nMinute <= 0 -> {
                nHour -= 1
                nMinute = 60 - nMinute
            }
        }
        return Time(nHour, nMinute)
    }
    operator fun plus(other: Int):Time{
        var nMinute = minute + other%60
        var nHour = hour + other/60

        when{
            nHour >= 24 -> nHour = 0
            nHour < 0 -> nHour = 0
        }
        when{
            nMinute >= 60 -> {
                nHour += 1
                nMinute = 0
            }
            nMinute <= 0 -> {
                nHour -= 1
                nMinute = 60 - nMinute
            }
        }
        return Time(nHour, nMinute)
    }

    operator fun minus(other: Time):Time{
        var nHour = hour - other.hour
        var nMinute = minute - other.minute
        when{
            nHour >= 24 -> nHour = 0
            nHour < 0 -> nHour = 0
        }
        when{
            nMinute >= 60 -> {
                nHour += 1
                nMinute = 0
            }
            nMinute <= 0 -> {
                nHour -= 1
                nMinute = 60 - nMinute
            }
        }
        return Time(nHour, nMinute)
    }

    override operator fun compareTo(other: Time) = when{
        hour != other.hour -> hour - other.hour
        else -> minute - other.minute
    }

    override fun toString(): String {
        val min = if(minute == 0) "00" else minute.toString()
        return hour.toString()+":"+ min
    }
}

@Parcelize
class WorkTime(val start: Time, val end: Time):Parcelable{

    override fun toString(): String {
        return start.toString()+"~"+end.toString()
    }

    fun getLength():Time{
        return end - start
    }

    fun equals(other: WorkTime): Boolean {
        return(start.equals(other.start) && end.equals(other.end))
    }
}

fun roundUp(num: Long, divisor: Long): Long {
    return (num + divisor - 1) / divisor
}

@Parcelize
class WorkDay(val dayOfWeek: Week, val workTime: WorkTime): Parcelable

enum class Status{ Accepted, Declined, None }

@Parcelize
class BookRecord(
    var date: Date,
    var time: Time,
    var hospital:Hospital,
    var status: Status
):Parcelable


