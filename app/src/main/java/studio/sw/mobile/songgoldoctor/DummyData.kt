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
        fun generateWeek(): Week {
            val weeks = arrayListOf<Week>(
                Week.Friday, Week.Holiday, Week.Monday,
                Week.Saturday, Week.Sunday, Week.Thursday,
                Week.Tuesday, Week.Wednesday
            )
            return weeks[Random().nextInt(weeks.size)]
        }
        @JvmStatic
        fun generateStatus():Status{
            val status = arrayListOf<Status>(
                Status.Accepted,
                Status.Declined,
                Status.None
            )
            return status[Random().nextInt(status.size)]
        }
        @JvmStatic
        fun generateWorkDays(): ArrayList<WorkDay> {
            val workDays = ArrayList<WorkDay>()
            workDays.add(WorkDay(Week.Monday, WorkTime(Time(9,0),Time(18,0))))
            workDays.add(WorkDay(Week.Tuesday, WorkTime(Time(9,0),Time(18,0))))
            workDays.add(WorkDay(Week.Wednesday, WorkTime(Time(9,0),Time(18,0))))
            workDays.add(WorkDay(Week.Thursday, WorkTime(Time(9,0),Time(18,0))))
            workDays.add(WorkDay(Week.Friday, WorkTime(Time(10,0),Time(17,0))))
            workDays.add(WorkDay(Week.Saturday, WorkTime(Time(10,0),Time(17,0))))
            return workDays
        }

        @JvmStatic
        fun generateDepartments(): ArrayList<Department> {
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
            repeat(6) {
                val department = deps[Random().nextInt(deps.size)]
                if (department !in value) {
                    value.add(department)
                }
            }
            return value
        }

        @JvmStatic
        fun IntRangeRandom(start: Int, endInclusive: Int): Int {
            return Random().nextInt((endInclusive + 1) - start) + start
        }

        @JvmStatic
        fun DoubleRangeRandom(start: Double, endInclusive: Double): Double {
            return start + (endInclusive - start) * Random().nextDouble()
        }
        @JvmStatic
        fun BeautifulHospital():Hospital{
            val nameArray = arrayOf("행복병원", "송골병원", "항공병원", "공대병원")
            val addressArray = arrayOf("특별택시 마구마구 포장도로 104", "파주시 강동구 을지3로 451-5")
            val phoneArray = arrayOf("01045680975","024568839","03182822952")
            val idArray = arrayOf("01","02","03","04")
            val position = LatLng(
                DummyData.DoubleRangeRandom(
                    37.59, 37.6
                ),
                DummyData.DoubleRangeRandom(
                    126.86, 126.87
                ))
            val workDays  = generateWorkDays()
            val departments = generateDepartments()
            return Hospital(
                nameArray[Random().nextInt(nameArray.size)],
                phoneArray[Random().nextInt(phoneArray.size)],
                idArray[Random().nextInt(idArray.size)],
                addressArray[Random().nextInt(addressArray.size)],
                position,
                workDays,
                departments
            )
        }
        @JvmStatic
        fun dummyHospital(): Hospital {
            return Hospital(
                generateText(10),
                generateText(10),
                Random().ints(10).toString(),
                generateText(15),
                LatLng(
                    DummyData.DoubleRangeRandom(
                        37.59, 37.6
                    ),
                    DummyData.DoubleRangeRandom(
                        126.86, 126.87
                    )
                ),
                generateWorkDays(),
                generateDepartments()

            )
        }
        @JvmStatic
        fun dummyHospitalWithRange(center:LatLng?, radius:Double):Hospital{
            if (center != null){
                val latlng = LatLng(DoubleRangeRandom(center.longitude-radius,center.longitude+radius),
                    DoubleRangeRandom(center.latitude-radius, center.latitude+radius))
                return Hospital(
                    generateText(10),
                    generateText(10),
                    Random().ints(10).toString(),
                    generateText(15),
                    latlng,
                    generateWorkDays(),
                    generateDepartments()

                )
            }

            return dummyHospital()

        }
        @JvmStatic
        fun dummyBookRecored():BookRecord{
            var rnd = Random()
            var ms = -946771200000L + Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)

            var dt = Date(ms)

            return BookRecord(
                dt,
                Time(15,0),
                dummyHospital(),
                generateStatus()
            )
        }

        fun dummyDate(): Date {
            return Date(Random().nextInt(19) + 2000, Random().nextInt(12) + 1, Random().nextInt(28) + 1)
        }

        fun dummyDiagnosis(): Diagnosis {
            return Diagnosis(
                generateText(10),
                dummyDate(),
                generateText(10),
                Department.AnE,
                generateText(10),
                generateText(10),
                DiagnosisType.Estimation,
                dummyDate(),
                dummyDate(),
                generateText(10),
                Random().nextInt(3) + 1
            )
        }

        fun showDummyDiagnosis1(): Diagnosis {
            return Diagnosis(generateText(10), Date(2017,3,10), "크리스탈병원", Department.AnE, "마데카솔", generateText(10) , DiagnosisType.Estimation,
                dummyDate(), dummyDate(), "심각한 외상", 2)
        }

        fun showDummyDiagnosis2(): Diagnosis {
            return Diagnosis(generateText(10), Date(2017,11,24), "송골병원", Department.AnE, "발모제", generateText(10) , DiagnosisType.Estimation,
                dummyDate(), dummyDate(), "탈모", 2)
        }
    }
}