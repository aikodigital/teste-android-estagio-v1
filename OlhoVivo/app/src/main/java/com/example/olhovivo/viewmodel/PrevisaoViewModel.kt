import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.olhovivo.model.PrevisaoResponse
import com.example.olhovivo.repository.PrevisaoRepository
import kotlinx.coroutines.launch

class PrevisaoViewModel(private val repository: PrevisaoRepository) : ViewModel() {
    private val _previsaoLiveData = MutableLiveData<PrevisaoResponse>()
    val previsaoLiveData: LiveData<PrevisaoResponse> = _previsaoLiveData

    fun fetchPrevisao(codigoParada: Int, codigoLinha: Int) {
        viewModelScope.launch {
            try {
                val previsao = repository.getPrevisao(codigoParada, codigoLinha)
                _previsaoLiveData.postValue(previsao)
            } catch (e: Exception) {
                // Handle the exception (e.g., show error message)
            }
        }
    }
}
