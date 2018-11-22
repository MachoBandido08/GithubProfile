package com.labo.githubprofile

import android.os.AsyncTask
import com.squareup.moshi.Moshi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL


class   FetchUserTask(mainActivity: MainActivity) : AsyncTask<String, User, User?>() {
    private val activityRef: WeakReference<MainActivity> = WeakReference(mainActivity)

    override fun doInBackground(vararg p0: String): User? {
        val mainActivity: MainActivity = activityRef.get() ?: return null

        val dao = AppDatabase.getDbInstance(mainActivity)!!.userDao()

        val userName = p0[0]

        var user = dao.getUserByLogin(userName)

        if (!isCancelled) {
            if (user != null) {
                publishProgress(user)
            }

            val userJson: String = fetchUserJson(userName)

            if (!userJson.isEmpty()) {
                // TODO: Utiliser Moshi afin de convertir le JSON en objet de la classe User
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter<User>(User::class.java)
                user = jsonAdapter.fromJson(userJson)
                if (user != null) {
                    dao.insertUser(user)
                }
            }
        }
        return user
    }

    override fun onProgressUpdate(vararg values: User) {
        super.onProgressUpdate(*values)

        var user = values[0]

        val mainActivity: MainActivity = activityRef.get() ?: return

        mainActivity.displayUser(user, true)
    }

    override fun onPostExecute(user: User?) {

        super.onPostExecute(user)

        val mainActivity: MainActivity = activityRef.get() ?: return

        if (user == null)
            mainActivity.displayErrorMessage()
        else
            mainActivity.displayUser(user, false)
    }

    private fun fetchUserJson(userName: String): String {
        val urlStr = "https://api.github.com/users/" + userName
        val url = URL(urlStr)
        var responseStr = ""
        with(url.openConnection() as HttpURLConnection) {
            try {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    responseStr = readResponse(BufferedReader(InputStreamReader(inputStream)))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return responseStr
    }

    private fun readResponse(bufferedReader: BufferedReader): String {
        val response = StringBuffer()

        var inputLine = bufferedReader.readLine()
        while (inputLine != null) {

            response.append(inputLine)
            inputLine = bufferedReader.readLine()
        }

        return response.toString()
    }
}