package studio.sw.mobile.songgoldoctor

import com.google.android.gms.maps.model.LatLng
import java.lang.Math.pow
import java.util.*
import kotlin.collections.ArrayList


class DummyData {
    companion object {
        @JvmStatic
        fun generateText(size: Long): String {
            val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val string: String = Random().ints(
                size,
                0,
                source.length - 1
            )
                .toArray()
                .map(source::get)
                .joinToString("")
            return string
        }
        @JvmStatic
        fun generateWeek():Week{
            val weeks = arrayListOf<Week>(
                Week.Friday, Week.Holiday, Week.Monday,
                Week.Saturday, Week.Sunday, Week.Thursday,
                Week.Tuesday, Week.Wednesday
            )
            return weeks[Random().nextInt(weeks.size)]
        }
        @JvmStatic
        fun normalWorkTime():ArrayList<WorkTime>{
            val workTimes = ArrayList<WorkTime>()
            workTimes.add(WorkTime(Time(9,0),Time(12,0)))
            workTimes.add(WorkTime(Time(13,30),Time(18,0)))
            return workTimes
        }
        @JvmStatic
        fun shortWorkTime():ArrayList<WorkTime>{
            val workTimes = ArrayList<WorkTime>()
            workTimes.add(WorkTime(Time(10,0),Time(12,0)))
            workTimes.add(WorkTime(Time(13,30),Time(17,0)))
            return workTimes
        }
        @JvmStatic
        fun generateWorkDays():ArrayList<WorkDay>{
            val workDays = ArrayList<WorkDay>()
            workDays.add(WorkDay(Week.Monday, normalWorkTime()))
            workDays.add(WorkDay(Week.Tuesday, normalWorkTime()))
            workDays.add(WorkDay(Week.Wednesday, normalWorkTime()))
            workDays.add(WorkDay(Week.Thursday, normalWorkTime()))
            workDays.add(WorkDay(Week.Friday, shortWorkTime()))
            workDays.add(WorkDay(Week.Saturday, shortWorkTime()))
            return workDays
        }
        @JvmStatic
        fun generateDepartments():ArrayList<Department>{
            val deps = arrayListOf(
                Department.AnE,
                Department.Anaesthetics,
                Department.Cardiology,
                Department.Chaplaincy,
                Department.CriticalCare,
                Department.DiagnosticImaging,
                Department.ENT,
                Department.Gastroenterology,
                Department.GeneralSurgery,
                Department.Gynaecology,
                Department.Haematology,
                Department.Microbiology,
                Department.Neurology
            )
            val value = ArrayList<Department>()
            repeat(6){
                val department = deps[Random().nextInt(deps.size)]
                if(department !in value){
                    value.add(department)
                }
            }
            return value
        }
        @JvmStatic
        fun IntRangeRandom(start:Int, endInclusive:Int):Int{
            return Random().nextInt((endInclusive + 1) - start)+  start
        }
        @JvmStatic
        fun DoubleRangeRandom(start:Double, endInclusive:Double):Double{
            return start + (endInclusive - start) * Random().nextDouble()
        }

        @JvmStatic
        fun dummyHospital(): Hospital {
            return Hospital(generateText(10),
                generateText(10),
                Random().ints(10).toString(),
                generateText(15),
                LatLng(
                    DummyData.DoubleRangeRandom(
                        37.59,37.6),
                    DummyData.DoubleRangeRandom(
                        126.86, 126.87)),
                generateWorkDays(),
                generateDepartments()

            )
        }
    }
}