package com.labo.githubprofile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var fetchUserTask: FetchUserTask? = null
    var savedAvatarUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpSearchView()

        if (savedInstanceState != null) {
            // TODO: restaurer l'état
            layout_profile.visibility = View.VISIBLE
            tv_user_name.text = savedInstanceState.getString("user_name")
            tv_fullname.text = savedInstanceState.getString("full_name")
            Glide.with(iv_avatar).load(savedInstanceState.getString("avatar")).into(iv_avatar)
            savedAvatarUrl = savedInstanceState.getString("avatar")
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        // Save the user's current game state
        outState?.let {
            it.putString("user_name", tv_user_name.text.toString())
            it.putString("full_name", tv_fullname.text.toString())
            it.putString("avatar", savedAvatarUrl)
        }
        super.onSaveInstanceState(outState)
    }

    private fun setUpSearchView() {
        search_view_user.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUser(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        search_view_user.isSubmitButtonEnabled = true

    }

    private fun searchUser(userName: String?) {
        layout_profile.visibility = View.GONE
        progress_bar_profile.visibility = View.VISIBLE
        if (fetchUserTask != null)
            fetchUserTask!!.cancel(true)

        fetchUserTask = FetchUserTask(this)
        fetchUserTask!!.execute(userName)

    }


    fun displayErrorMessage() {
        progress_bar_profile.visibility = View.GONE
        Toast.makeText(this, this.getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }

    fun displayUser(user: User, loading: Boolean) {
        layout_profile.visibility = View.VISIBLE
        tv_user_name.text = user.login
        tv_fullname.text = user.name
        savedAvatarUrl = user.avatar_url
        Glide.with(iv_avatar).load(user.avatar_url).into(iv_avatar)
        if (!loading)
            progress_bar_profile.visibility = View.GONE
    }

}



