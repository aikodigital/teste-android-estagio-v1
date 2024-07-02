package br.com.aiko.estagio.bussp.model

import br.com.aiko.estagio.bussp.data.remote.response.LinhasLocalizada
import br.com.aiko.estagio.bussp.data.remote.response.PontoParada
import br.com.aiko.estagio.bussp.data.remote.response.VeiculoLocalizado

data class PrevisaoParadaPorParada(
    val map: MutableMap<LinhasLocalizada, List<VeiculoLocalizado>>
)

