package agh.vote7.login.data.login

import agh.vote7.login.data.Result
import agh.vote7.login.data.model.LoggedInUser
import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val client = OkHttpClient()
    val gson = Gson()
    private val loginUrl = "http://192.168.43.77:8080/users/login"
    private val getUserDataUrl = "http://192.168.43.77:8080/users/me"
    private val logoutUrl = "http://192.168.43.77:8080/users/logout"
    private val JSON = MediaType.parse("application/json; charset=utf-8")
    private var token : String? = null
    private var user : LoggedInUser? = null
    private var fail = false

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            PostLogin().execute(username, password).get()
            GetUserData().execute().get()
            if (fail)
                throw Throwable()

            return Result.Success(user!!)
        } catch (e: Throwable) {
            Log.e("dbg", "logging error")
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        try {
            GetLogout().execute()
        }catch (e: Throwable){
            e.printStackTrace()
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class PostLogin : AsyncTask<Any, Any, Any>()  {
        override fun doInBackground(vararg params: Any?){

            val json = JSONObject()
            json.put("email", params[0])
            json.put("password", params[1])

            val body = RequestBody.create(JSON, json.toString())
            val request = Request.Builder()
                .url(loginUrl)
                .post(body)
                .build()

            try {
                val response = client.newCall(request).execute()
                val jsonToken = JSONObject(response.body()?.string()!!)
                Log.e("dbg", jsonToken.getString("token"))
                Log.e("dbg", "xd")
                jsonToken.getString("token")
            }catch (e: Exception){
                fail = true
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetUserData : AsyncTask<Any, Any, Any>() {
        override fun doInBackground(vararg params: Any?){

            val request = Request.Builder()
                .url("$getUserDataUrl?token=$token")
                .get()
                .build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body()?.string()!!
                user = gson.fromJson(responseBody, LoggedInUser::class.java)
                Log.e("dbg", user.toString())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetLogout : AsyncTask<Any, Any, Any>() {
        override fun doInBackground(vararg params: Any?){

            val request = Request.Builder()
                .url("$logoutUrl?token=$token")
                .get()
                .build()

            try {
                val response = client.newCall(request).execute()
                Log.e("dbg", response.body()?.string()!!)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }


}

