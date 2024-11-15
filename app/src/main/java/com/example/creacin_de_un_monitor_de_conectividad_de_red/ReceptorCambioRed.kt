package com.example.creacin_de_un_monitor_de_conectividad_de_red
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Clase que extiende BroadcastReceiver para monitorizar cambios en el estado de la red.
 * Toma un callback que actualiza la interfaz de usuario con el estado de la conexión.
 */

class ReceptorCambioRed (private val alCambiarEstadoRed: (String) -> Unit) : BroadcastReceiver(){
    override fun onReceive(contexto: Context?, intent: Intent?) {
        if (contexto != null) {
            // Obtener el gestor de conectividad del sistema
            val gestorConectividad = contexto.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val redActiva = gestorConectividad.activeNetwork

            // Determinar el estado de la conexión
            val estado = if (redActiva != null) {
                val capacidades = gestorConectividad.getNetworkCapabilities(redActiva)
                when {
                    capacidades?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "Conectado a Wi-Fi"
                    capacidades?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "Conectado a Datos Móviles"
                    else -> "Sin Conexión"
                }
            } else {
                "Sin Conexión"
            }

            // Llamar al callback para actualizar el estado en la UI
            alCambiarEstadoRed(estado)
        }
    }

}