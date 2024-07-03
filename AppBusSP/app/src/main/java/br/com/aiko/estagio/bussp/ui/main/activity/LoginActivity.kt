package br.com.aiko.estagio.bussp.ui.main.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.aiko.estagio.bussp.databinding.ActivityLoginBinding
import br.com.aiko.estagio.bussp.ui.main.MainActivity
import br.com.aiko.estagio.bussp.ui.main.utils.dialogs.Dialogs
import br.com.aiko.estagio.bussp.ui.main.viewmodel.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        if (isConectado(this)) {
            authenticationViewModel.authentication("5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4")
            authenticationViewModel.authentication.observe(this) { isAuthenticated ->
                if (isAuthenticated) {
                    binding.btnConectar.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.btnReConectar.visibility = View.INVISIBLE
                    binding.btnConectar.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    binding.progressBar.visibility = View.GONE
                    Dialogs.showErrorMaterialDialog("Não foi possível conectar-se à API.", this)
                }
            }
        } else {
            binding.progressBar.visibility = View.GONE
            Dialogs.showErrorMaterialDialog(
                "Não foi possível conectar à internet, verifique sua conexão.",
                this
            )
            binding.btnConectar.visibility = View.GONE
            binding.btnReConectar.visibility = View.VISIBLE
            binding.btnReConectar.setOnClickListener {
                recreate()
            }
        }

    }

    private fun isConectado(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}