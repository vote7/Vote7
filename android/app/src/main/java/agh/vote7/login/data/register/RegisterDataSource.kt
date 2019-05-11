package agh.vote7.login.data.register

import agh.vote7.login.data.Result
import agh.vote7.login.data.model.LoggedInUser
import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.IOException

class RegisterDataSource {
    private val client = OkHttpClient()
    val gson = Gson()
    private val registerUrl = "http://192.168.1.17:8080/users/register"
    private val JSON = MediaType.parse("application/json; charset=utf-8")
    private var token : String? = null

    fun register(name: String, surname: String, email: String, password: String): Result<Any> {
        try {
            PostRegister().execute(name, surname, email, password).get()
            return Result.Success(true)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error during registering", e))
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class PostRegister : AsyncTask<Any, Any, Any>() {
        override fun doInBackground(vararg params: Any?) {

            val json = JSONObject()
            json.put("name", params[0])
            json.put("surname", params[1])
            json.put("email", params[2])
            json.put("password", params[3])

            val body = RequestBody.create(JSON, json.toString())
            val request = Request.Builder()
                .url(registerUrl)
                .post(body)
                .build()

            try {
                Log.e("register", json.toString())
                val response = client.newCall(request).execute()
                val jsonToken = JSONObject(response.body()?.string()!!)
                Log.e("register", jsonToken.getString("token"))
                token = jsonToken.getString("token")
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}