package com.example.fullonnettestapp.data.repository

import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class RandomUserResponse(
    @SerializedName("results")
    val results: List<RandomUserItem>,
)

data class RandomUserItem(
    @SerializedName("name")
    val name:RandomUserName,
    @SerializedName("picture")
    val picture:RandomUserPicture?,
)

data class RandomUserName(
    @SerializedName("title")
    val title: String,
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String,
)

data class RandomUserPicture(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
)

interface UsersRepository {
    val usersFlow: Flow<List<RandomUserItem>>
}

class UsersRetrofitRepository : UsersRepository {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service: UserService = retrofit.create(UserService::class.java)

    override val usersFlow: Flow<List<RandomUserItem>>
        get() = flow {
            val list = service.getUsers(25).results
            Log.d("JMMLOG", "usersFlow: $list"  )
            emit(list)
        }

}

interface UserService {
    @GET("api/")
    suspend fun getUsers(
        @Query("results") results: Int,
        @Query("inc") inc:String = "name,picture",
        @Query("noinfo") noinfo: Boolean = true
    ): RandomUserResponse
}