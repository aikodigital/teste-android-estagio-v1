package com.example.teste.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.teste.R
import com.example.teste.models.LinePosition
import com.example.teste.models.VehiclePosition
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class LineInfoBottomSheetFragment(
    private val vehiclePosition: VehiclePosition,
    private val linePosition: LinePosition
) : BottomSheetDialogFragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val textViewVehicleId = rootView.findViewById<TextView>(R.id.textViewLineId)
        val textViewDetails = rootView.findViewById<TextView>(R.id.textViewLineDetails)

        val formattedTime = formatUtcToLocal(vehiclePosition.ta)


        textViewVehicleId.text = "Veículo: ${vehiclePosition.p}"
        textViewDetails.text = """
            Veículo com Acessibilidade: ${if (vehiclePosition.a) "Sim" else "Não"}
            Horário de captura da Localização: $formattedTime
            Origem da Linha: ${linePosition.lt0}
            Destino da Linha: ${linePosition.lt1}
        """.trimIndent()
    }

    private fun formatUtcToLocal(utcTime: String): String {
        return try {
            val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            utcFormat.timeZone = TimeZone.getTimeZone("UTC")

            val localFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            localFormat.timeZone = TimeZone.getTimeZone("GMT-3")

            val date = utcFormat.parse(utcTime)
            localFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            "Data inválida"
        }
    }
}
