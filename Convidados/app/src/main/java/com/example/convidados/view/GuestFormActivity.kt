package com.example.convidados.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.viewmodel.GuestFormViewModel
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)
        binding.buttonEnviar.setOnClickListener(this)
        binding.radioPresent.isChecked = true
        observe()
        loadData()
    }

    override fun onClick(view: View) {
        if(view.id == R.id.button_enviar){
            val name = binding.editTextName.text.toString()
            val presence = binding.radioPresent.isChecked
            val age = binding.editTextAge.text.toString()
            val ageInt = age.toInt()
            val gender = binding.editTextName.text.toString()
            val model = GuestModel(guestId, name, presence, ageInt, gender)

            viewModel.save(model)
            finish()
        }
    }

    private fun observe(){
        viewModel.guest.observe(this, Observer {
            binding.editTextName.setText(it.name)
            if(it.presence){
                binding.radioPresent.isChecked = true
            }else{
                binding.radioAbsent.isChecked = true
            }
        })

        viewModel.saveGuest.observe(this, Observer {
            if(it.sucess){
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if(bundle != null){
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.get(guestId)
        }
    }
}