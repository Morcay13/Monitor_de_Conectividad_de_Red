package com.example.creacin_de_un_monitor_de_conectividad_de_red

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad principal de la aplicación.
 * Monitoriza los cambios en la red y actualiza la interfaz de usuario.
 */

class MainActivity : AppCompatActivity() {
    // Declarar el BroadcastReceiver para monitorizar la red
    private lateinit var receptorCambioRed: ReceptorCambioRed

    override fun onCreate(estadoInstancia: Bundle?) {
        super.onCreate(estadoInstancia)
        setContentView(R.layout.activity_main)

        // Referencia al TextView que muestra el estado de la red
        val tvEstadoRed: TextView = findViewById(R.id.tvEstadoRed)

        // Crear instancia del receptor con un callback para actualizar el TextView
        receptorCambioRed = ReceptorCambioRed { estado ->
            runOnUiThread {
                tvEstadoRed.text = "Estado de la red: $estado"
            }
        }

        // Registrar el receptor para escuchar cambios en la conectividad
        val filtroIntent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receptorCambioRed, filtroIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Anular el registro del receptor al destruir la actividad
        unregisterReceiver(receptorCambioRed)
    }
}
