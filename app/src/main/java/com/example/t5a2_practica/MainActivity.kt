package com.example.t5a2_practica

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //---------------------------------------------------------------------------
    //CAMBIAR ENTRE CUENTA PROPIA I AJENA

    private lateinit var spinner2: Spinner
    private lateinit var etNumeroCuenta: EditText
    private lateinit var etImporte: EditText
    private lateinit var switchJustifiante: CheckBox
    private lateinit var btnMostrarMensaje: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner2 = findViewById(R.id.spinner2)
        etNumeroCuenta = findViewById(R.id.etNumeroCuenta)
        etImporte = findViewById(R.id.etCantidad)
        switchJustifiante = findViewById(R.id.justificante)
        btnMostrarMensaje = findViewById(R.id.butEnviar)

        val radioGroup = findViewById<RadioGroup>(R.id.radiogroup)
        val propiaRadioButton = findViewById<RadioButton>(R.id.propia)

        //cuenta propia por defecto
        propiaRadioButton.isChecked = true;

        //configurar el listener
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.propia) {
                spinner2.visibility = View.VISIBLE
                etNumeroCuenta.visibility = View.GONE
            } else if (checkedId == R.id.ajena) {
                spinner2.visibility = View.GONE
                etNumeroCuenta.visibility = View.VISIBLE
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }











        //---------------------------------------------------------------------------
        //CREACIÓ DE SPINNERS

        val spNumeros:Spinner = findViewById<Spinner>(R.id.spinner)
        val numeros = resources.getStringArray(R.array.numeros_cuentas)
        val adapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,numeros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNumeros.adapter = adapter

        val spNumeros2:Spinner = spinner2
        val numeros2 = resources.getStringArray(R.array.numeros_cuentas)
        val adapter2= ArrayAdapter(this,android.R.layout.simple_spinner_item,numeros2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNumeros2.adapter = adapter2

        val spDivisas:Spinner = findViewById<Spinner>(R.id.divisas)
        val divisas = resources.getStringArray(R.array.strdivisas)
        val adapter3= ArrayAdapter(this,android.R.layout.simple_spinner_item,divisas)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDivisas.adapter = adapter3

        btnMostrarMensaje.setOnClickListener {
            val cuentaOrigen = spNumeros.selectedItem.toString()
            val cuentaDestino = if (radioGroup.checkedRadioButtonId == R.id.propia) {
                spinner2.selectedItem.toString()
            } else {
                etNumeroCuenta.text.toString()
            }
            val importe = etImporte.text.toString()
            val divisa = spDivisas.selectedItem.toString()
            val justificante = if (switchJustifiante.isChecked) "Enviar Justificante" else ""

            val mensaje = """
                Cuenta origen: $cuentaOrigen
                Cuenta destino: $cuentaDestino
                Importe: $importe $divisa
                $justificante
             """.trimIndent()

            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

        }
    }
}