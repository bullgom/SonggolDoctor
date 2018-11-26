package studio.sw.mobile.songgoldoctor

import java.lang.Math.pow
import java.util.*


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
        fun dummyHospital(): Hospital {
            return Hospital(generateText(10), generateText(10), Random().ints(10).toString())
        }

        fun dummyDate(): Date {
            return Date(randBetween(2000,2018), randBetween(1, 12), randBetween(1, 28))
        }
        fun randBetween(start: Int, end: Int): Int {
            return start + Math.round(Math.random() * (end - start)).toInt()
        }

        fun dummyDiagnosis(): Diagnosis {
            return Diagnosis(generateText(10), dummyDate(), generateText(10), Department.AnE,generateText(10), generateText(10) , DiagnosisType.Estimation,
                dummyDate(), dummyDate(), generateText(10), randBetween(1, 3))
        }
    }
}