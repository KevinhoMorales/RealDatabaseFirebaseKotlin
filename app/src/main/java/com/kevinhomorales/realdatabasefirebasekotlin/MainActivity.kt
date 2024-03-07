package com.kevinhomorales.realdatabasefirebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kevinhomorales.realdatabasefirebasekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }


    private fun setUpView() {
        // Inicializa Firebase
        database = FirebaseDatabase.getInstance().reference
        setUpActions()
    }

    private fun setUpActions() {
        binding.uploadButtonId.setOnClickListener {
            uploadData()
        }
        binding.downloadButtonId.setOnClickListener {
            downloadData()
        }
        binding.updateButtonId.setOnClickListener {
            updateData("ACTUALIZACIÓN DE RDB")
        }
        binding.removeButtonId.setOnClickListener {
            removeData()
        }
    }

    // Escribe datos en la base de datos
    fun uploadData() {
        val data = "¡HOLA RDB!"
        database.child("ruta/data").setValue(data)
    }

    // Lee datos de la base de datos
    fun downloadData() {
        database.child("ruta/data").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dato = snapshot.getValue(String::class.java)
                // Manejar el dato leído
                binding.textId.setText(dato)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores de lectura
                print(error.message)
            }
        })
    }

    // Actualiza datos en la base de datos
    fun updateData(newData: String) {
        val actualizacion = HashMap<String, Any>()
        actualizacion["data"] = newData
        database.child("ruta").updateChildren(actualizacion)
    }

    // Elimina datos de la base de datos
    fun removeData() {
        val data = "RDB ELIMINADA"
        database.child("ruta/data").removeValue()
        database.child("ruta/data").setValue(data)
        binding.textId.setText(data)
    }
}