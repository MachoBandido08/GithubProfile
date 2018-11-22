package com.labo.githubprofile

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @Json(name = "login") var login: String? = "",
    @Json(name = "id") var id: Int? = 0,
    @Json(name = "avatar_url") var avatar_url: String? = "",
    @Json(name = "gravatar_id") var gravatar_id: String? = "",
    @Json(name = "url") var url: String? = "",
    @Json(name = "html_url") var html_url: String? = "",
    @Json(name = "followers_url") var followers_url: String? = "",
    @Json(name = "following_url") var following_url: String? = "",
    @Json(name = "gists_url") var gists_url: String? = "",
    @Json(name = "starred_url") var starred_url: String? = "",
    @Json(name = "subscriptions_url") var subscriptions_url: String? = "",
    @Json(name = "organizations_url") var organizations_url: String? = "",
    @Json(name = "repos_url") var repos_url: String? = "",
    @Json(name = "events_url") var events_url: String? = "",
    @Json(name = "received_events_url") var received_events_url: String? = "",
    @Json(name = "type") var type: String? = "",
    @Json(name = "site_admin") var site_admin: Boolean? = false,
    @Json(name = "name") var name: String? = "",
    @Json(name = "company") var company: String? = "",
    @Json(name = "blog") var blog: String? = "",
    @Json(name = "location") var location: String? = "",
    @Json(name = "email") var email: String? = "",
    @Json(name = "hireable") var hireable: Boolean? = false,
    @Json(name = "bio") var bio: String? = "",
    @Json(name = "public_repos") var public_repos: Int? = 0,
    @Json(name = "public_gists") var public_gists: Int? = 0,
    @Json(name = "followers") var followers: Int? = 0,
    @Json(name = "following") var following: Int? = 0,
    @Json(name = "created_at") var created_at: String? = "",
    @Json(name = "updated_at") var updated_at: String? = ""
)
