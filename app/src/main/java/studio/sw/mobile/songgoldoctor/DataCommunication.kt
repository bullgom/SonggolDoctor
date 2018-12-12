package studio.sw.mobile.songgoldoctor

import android.os.AsyncTask
import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

/**
 * Created by jaewook on 29/11/2018.
 */
class DataCommunication() : AsyncTask<String, Void, String>() {


    override fun doInBackground(vararg params: String?): String {
        Log.println(Log.DEBUG, "", "TEST: It works!")
        FuelManager.instance.basePath = "http://192.168.170.182:3000"
        val URL = params[0]
        "${URL}".httpGet().response { Request, Response, Result ->
            Log.println(Log.DEBUG, "doInBackground", Result.get().toString(charset("UTF-8")));
        }
        return "TEST"
    }

    override fun onPostExecute(result: String?) {
        Log.println(Log.DEBUG, "onPostExecute", "$result");
    }
}

class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }
}