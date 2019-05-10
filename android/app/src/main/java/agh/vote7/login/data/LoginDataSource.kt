package agh.vote7.login.data

import agh.vote7.login.data.model.LoggedInUser
import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private val client = OkHttpClient()
    private val url = "http://192.168.43.77:8080/api/users/register"
    private val JSON = MediaType.parse("application/json; charset=utf-8")

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            val poster = PostLogin()
            poster.execute()
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    @SuppressLint("StaticFieldLeak")
    inner class PostLogin : AsyncTask<Any, Any, Any>() {
        override fun doInBackground(vararg params: Any?): Any {
            val json = JSONObject()

            json.put("email", "mail@gmail.com")
            json.put("password", "heheszki")
            json.put("name", "michal")
            json.put("surname", "kowalski")


            val body = RequestBody.create(JSON, json.toString())
            Log.e("dbg", json.toString())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()
            print("\n\n\n\n\n\n")
            try {
                val response = client.newCall(request).execute()
                Log.e("dbg", response.toString())
            }catch (e: Exception){
                e.printStackTrace()
            }
            return "xd"
        }

    }




}

