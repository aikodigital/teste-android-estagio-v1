package br.com.aiko.estagio.bussp.data.repository

import android.util.Log
import br.com.aiko.estagio.bussp.data.remote.RemoteDataSource
import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.remote.response.Empresas
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.data.remote.response.PontoParada
import br.com.aiko.estagio.bussp.data.remote.response.PontoParadaLinha
import br.com.aiko.estagio.bussp.data.remote.response.PosVeiculo
import br.com.aiko.estagio.bussp.data.remote.response.Posicao
import br.com.aiko.estagio.bussp.data.remote.response.PrevisaoChegada
import br.com.aiko.estagio.bussp.data.remote.response.PrevisaoChegadaLinha
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TransRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TransRepository {

    override suspend fun authentication(token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.authentication(token)
                if (response.isSuccessful) {
                    response.body() ?: false
                } else {
                    Log.e("Authentication", "${response.code()}")
                    false
                }
            } catch (e: Exception) {
                Log.e("Authentication", e.message.toString())
                false
            }
        }
    }

    override suspend fun buscarLinha(termosBusca: String): List<Linha> {
        val response = remoteDataSource.buscarLinha(termosBusca)

        return try {
            if (response.isSuccessful) {
                val linhas = response.body() ?: emptyList()
                return linhas
            } else {
                Log.e("BUSCAR LINHA", "${response.code()}")
                return emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR LINHA Execptin: ", e.message.toString())
            emptyList<Linha>()
        }
    }

    override suspend fun buscarLinhaSentido(termosBusca: String, sentido: Int): List<Linha> {
        val response = remoteDataSource.buscarLinhaSentido(termosBusca, sentido)
        return try {
            if (response.isSuccessful) {
                val linhas = response.body() ?: emptyList()
                linhas
            } else {
                Log.e("BUSCAR LINHA SENTINDO", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR LINHA SENTINDO Execption: ", e.message.toString())
            emptyList<Linha>()
        }
    }

    override suspend fun buscarParada(parada: String): List<Parada> {
        val response = remoteDataSource.buscarParada(parada)
        return try {
            if (response.isSuccessful) {
                val paradas = response.body() ?: emptyList()
                paradas
            } else {
                Log.e("BUSCAR PARADA", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR PARADA Exception: ", e.message.toString())
            emptyList<Parada>()
        }
    }

    override suspend fun buscarParadasPorLinha(codigoLinha: String): List<Parada> {
        val reponse = remoteDataSource.buscarParadasPorLinha(codigoLinha)
        return try {
            if (reponse.isSuccessful) {
                val paradas = reponse.body() ?: emptyList()
                paradas
            } else {
                Log.e("BUSCAR PARADAS POR LINHA", "${reponse.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR PARADAS POR LINHA Exception: ", e.message.toString())
            emptyList<Parada>()
        }
    }

    override suspend fun buscarParadasPorCorredor(codigoCorredor: Int): List<Parada> {
        val response = remoteDataSource.buscarParadasPorCorredor(codigoCorredor)
        return try {
            if (response.isSuccessful) {
                val paradas = response.body() ?: emptyList()
                paradas
            } else {
                Log.e("BUSCAR PARADA POR CORREDOR", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("BUSCAR PARADA POR CORREDOR Exception", e.message.toString())
            emptyList<Parada>()
        }
    }

    override suspend fun corredor(): List<Corredor> {
        val response = remoteDataSource.corredor()
        return try {
            if (response.isSuccessful) {
                val corredores = response.body() ?: emptyList()
                corredores
            } else {
                Log.e("CORREDORES", "${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("CORREDORES Exception: ", e.message.toString())
            emptyList<Corredor>()
        }
    }

    override suspend fun empresas(): Empresas {
        val response = remoteDataSource.empresas()
        return try {
            if (response.isSuccessful) {
                val empresas = response.body() ?: Empresas("", emptyList())
                empresas
            } else {
                Log.e("EMPRESAS", "${response.code()}")
                Empresas("", emptyList())
            }
        } catch (e: Exception) {
            Log.e("EMPRESAS Exception:", e.message.toString())
            Empresas("", emptyList())
        }
    }

    override suspend fun posicao(): Posicao {
        val response = remoteDataSource.posicao()
        return try {
            if (response.isSuccessful) {
                val posicao = response.body() ?: Posicao("", emptyList())
                posicao
            } else {
                Log.e("POSICAO", "${response.code()}")
                Posicao("", emptyList())
            }
        } catch (e: Exception) {
            Log.e("POSICAO Exception: ", e.message.toString())
            Posicao("", emptyList())
        }
    }

    override suspend fun posicaoLinha(codigoLinha: Int): PosVeiculo {
        val response = remoteDataSource.posicaoLinha(codigoLinha)
        return try {
            if (response.isSuccessful) {
                val posVeiculos = response.body() ?: PosVeiculo("", emptyList())
                posVeiculos
            } else {
                Log.e("POSICAO LINHA", "${response.code()}")
                PosVeiculo("", emptyList())
            }
        } catch (e: Exception) {
            Log.e("POSICAO LINHA Exception", e.message.toString())
            PosVeiculo("", emptyList())
        }
    }

    override suspend fun posicaoGaragem(codigoEmpresa: Int, codigoLinha: Int): Posicao {
        val response = remoteDataSource.posicaoGaragem(codigoEmpresa, codigoLinha)
        return try {
            if (response.isSuccessful) {
                val posico = response.body() ?: Posicao("", emptyList())
                posico
            } else {
                Log.e("POSICAO GARAGEM", "${response.code()}")
                Posicao("", emptyList())
            }
        } catch (e: Exception) {
            Log.e("POSICAO GARAGEM Exception", e.message.toString())
            Posicao("", emptyList())
        }
    }

    override suspend fun previsao(codigoParada: Int, codigoLinha: Int): PrevisaoChegada {
        val response = remoteDataSource.previsao(codigoParada, codigoLinha)
        return try {
            if (response.isSuccessful) {
                val previsao = response.body() ?: PrevisaoChegada(
                    "",
                    PontoParada(0, "", 0.0, 0.0, emptyList())
                )
                previsao
            } else {
                Log.e("PREVISAO", "${response.code()}")
                PrevisaoChegada("", PontoParada(0, "", 0.0, 0.0, emptyList()))
            }
        } catch (e: Exception) {
            Log.e("PREVISAO Exception:", e.message.toString())
            PrevisaoChegada("", PontoParada(0, "", 0.0, 0.0, emptyList()))
        }
    }

    override suspend fun previsaoLinha(codigoLinha: Int): PrevisaoChegadaLinha {
        val response = remoteDataSource.previsaoLinha(codigoLinha)
        return try {
            if (response.isSuccessful) {
                val previsao = response.body() ?: PrevisaoChegadaLinha("", emptyList())
                previsao
            } else {
                Log.e("PREVISAO LINHA", "${response.code()}")
                PrevisaoChegadaLinha("", emptyList())
            }
        } catch (e: Exception) {
            Log.e("PREVISAO LINHA Exception: ", e.message.toString())
            PrevisaoChegadaLinha("", emptyList())
        }
    }

    override suspend fun previsaoParada(codigoParada: Int): PrevisaoChegada {
        val response = remoteDataSource.previsaoParada(codigoParada)
        return try {
            if (response.isSuccessful) {
                val previsao = response.body() ?: PrevisaoChegada(
                    "",
                    PontoParada(0, "", 0.0, 0.0, emptyList())
                )
                previsao
            }else {
                Log.e("PREVISAO PARADA", "${response.code()}")
                PrevisaoChegada("", PontoParada(0, "", 0.0, 0.0, emptyList()))
            }
        }catch (e:Exception){
            Log.e("PREVISAO PARADA Exception:", e.message.toString())
            PrevisaoChegada("", PontoParada(0, "", 0.0, 0.0, emptyList()))
        }
    }
}
