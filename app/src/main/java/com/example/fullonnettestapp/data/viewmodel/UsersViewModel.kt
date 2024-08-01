package com.example.fullonnettestapp.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.fullonnettestapp.data.model.User
import com.example.fullonnettestapp.data.repository.UsersRepository
import kotlinx.coroutines.flow.map

class UsersViewModel(repository: UsersRepository) :ViewModel() {
    private val usersListFlow = repository.usersFlow.map {
        it.map { item ->
            User(
                firstname = item.name.first,
                lastname = item.name.last,
                photo = item.picture?.large?.let { Uri.parse(it) }
            )
        }
    }
    val userList: LiveData<List<User>> = usersListFlow.asLiveData()

}

class UsersViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}