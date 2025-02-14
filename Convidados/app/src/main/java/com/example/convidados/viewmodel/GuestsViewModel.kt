package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.convidados.model.GuestModel
import com.example.convidados.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application.applicationContext)
    private val listSAllGuests = MutableLiveData<List<GuestModel>>()
    val guests: LiveData<List<GuestModel>> = listSAllGuests

    fun getAll(){
        listSAllGuests.value = repository.getAll()
    }

    fun getPresent(){
        listSAllGuests.value = repository.getMarried()
    }

    fun getAbsent(){
        listSAllGuests.value = repository.getSingle()
    }

    fun delete(id: Int){
        repository.delete(id)
    }
}