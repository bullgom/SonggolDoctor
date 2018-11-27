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
            return Date(Random().nextInt(19) + 2000, Random().nextInt(12) + 1, Random().nextInt(28) + 1)
        }

        fun dummyDiagnosis(): Diagnosis {
            return Diagnosis(generateText(10), dummyDate(), generateText(10), Department.AnE,generateText(10), generateText(10) , DiagnosisType.Estimation,
                dummyDate(), dummyDate(), generateText(10), Random().nextInt(3) + 1)
        }
    }
}