package br.com.aiko.estagio.bussp.ui.main.utils.dialogs

import android.content.Context
import com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Dialogs {

    fun showErrorMaterialDialog(mensagem: String, context: Context) {
        MaterialAlertDialogBuilder(context, ThemeOverlay_MaterialComponents_MaterialAlertDialog)
            .setTitle("Erro")
            .setMessage(mensagem)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}

