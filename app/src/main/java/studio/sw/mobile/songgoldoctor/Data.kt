package studio.sw.mobile.songgoldoctor

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
        return hour.toString()+":"+minute.toString()
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
@Parcelize
class WorkDay(val dayOfWeek: Week, val workTimes: ArrayList<WorkTime>): Parcelable {
    fun isWorkingTime(dayOfWeek: Week, time: Time): Boolean {
        if (dayOfWeek != this.dayOfWeek) return false
        var isWorking = false
        for (workTime in workTimes) {
            if(time in workTime.start..workTime.end){
                isWorking = true
                break
            }
        }
        return isWorking
    }
    fun getWorkingTime():Time{
        var time:Time = Time(0,0)
        for(workTime in workTimes){
            time += workTime.start - workTime.end
        }
        return time
    }
}