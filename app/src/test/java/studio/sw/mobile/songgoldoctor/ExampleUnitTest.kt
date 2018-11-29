package studio.sw.mobile.songgoldoctor

import android.os.AsyncTask
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.junit.Test

import org.junit.Assert.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun dataCommunication(){
        println("TEST start");
        "http://localhost:3000/hosp/test".httpGet().responseString{request, response, result ->
            when(result){
                is Result.Failure ->{
                    val ex = result.getException()
                    println(ex);
                }
                is Result.Success -> {
                    val data = result.get()
                    println(data)
                }
            }
        }
        println("TEST end");
    }
}
