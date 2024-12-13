package com.example.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.convidados.model.GuestModel
import com.example.convidados.model.SucessFailure
import com.example.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GuestRepository.getInstance(application)
    private val guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = guestModel

    private val _saveguest = MutableLiveData<SucessFailure>()
    val saveGuest: LiveData<SucessFailure> = _saveguest

    fun save(guest: GuestModel) {
        val sucessFailure = SucessFailure(true, "")
        if (guest.id == 0) {
            sucessFailure.sucess = repository.insert(guest)
        } else {
            sucessFailure.sucess = repository.update(guest)
        }

    }

    fun get(id: Int) {
        guestModel.value = repository.get(id)
    }

}