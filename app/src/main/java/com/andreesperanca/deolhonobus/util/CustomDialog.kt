package com.andreesperanca.deolhonobus.util

import android.app.Dialog
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreesperanca.deolhonobus.adapters.ForecastDialogAdapter
import com.andreesperanca.deolhonobus.databinding.ButtomDialogItemBinding
import com.andreesperanca.deolhonobus.models.ListOfVehiclesLocated
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomDialog(
    private val listDialog: List<ListOfVehiclesLocated>,
    private val seeBusLineInMap: (latLng: LatLng, hour: String) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomDialog = super.onCreateDialog(savedInstanceState)
        val binding = ButtomDialogItemBinding.inflate(layoutInflater)
        bottomDialog.setContentView(binding.root)
        val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvBottomDialog.adapter = ForecastDialogAdapter(listDialog,seeBusLineInMap)
        binding.rvBottomDialog.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvBottomDialog.addItemDecoration(divisor)
        return bottomDialog
    }
}
