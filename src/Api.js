
export const postautorization = async () => {
    const url = "https://aiko-olhovivo-proxy.aikodigital.io"
    const loginurl = "/Login/Autenticar?token={0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e}"
    const aux = url + loginurl
    console.log(aux)
    try {
        const settings = {
            method: 'POST',
            headers: new Headers({
                'Authorization': 'Bearer 0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e',
                'Content-Type': 'application/json'
            })
        }

        const fetchResponse = await fetch(aux, settings)
        const data = await fetchResponse.json()
        console.log(data)
    }
    catch (error) {
        console.log(error)
    }
}

export const getprevision = async (codigoparada, codigolinha) => {
    const url = "https://aiko-olhovivo-proxy.aikodigital.io"
    const acess = `/Previsao?codigoParada=${codigoparada}&codigoLinha=${codigolinha}`
    const aux = url + acess
    console.log(aux)
    try {
        const settings = {
            method: 'GET',
            headers: new Headers({
                'Authorization': 'Bearer 0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e',
                'Content-Type': 'application/json'
            })
        }

        const fetchResponse = await fetch(aux, settings)
        const data = await fetchResponse.json()
        console.log(data)
        return data
    }
    catch (error) {
        console.log(error)
    }
}

export const getparada = async (buscaParada) => {
    const url = "https://aiko-olhovivo-proxy.aikodigital.io"
    const acess = `/Parada/Buscar?termosBusca=${buscaParada}`
    const aux = url + acess
    console.log(aux)
    try {
        const settings = {
            method: 'GET',
            headers: new Headers({
                'Authorization': 'Bearer 0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e',
                'Content-Type': 'application/json'
            })
        }

        const fetchResponse = await fetch(aux, settings)
        const data = await fetchResponse.json()
        console.log(data)
        return data
    }
    catch (error) {
        console.log(error)
    }
}

export const getlinhas = async (termo) => {

    const url = "https://aiko-olhovivo-proxy.aikodigital.io"
    const acess = `/Linha/Buscar?termosBusca=${termo}`
    const aux = url + acess
    console.log(aux)
    try {
        const settings = {
            method: 'GET',
            headers: new Headers({
                'Authorization': 'Bearer 0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e',
                'Content-Type': 'application/json'
            })
        }

        const fetchResponse = await fetch(aux, settings)
        const data = await fetchResponse.json()
        console.log(data)
        return data
    }
    catch (error) {
        console.log(error)
    }

}

export const getposition = async (termo) => {

    const url = "https://aiko-olhovivo-proxy.aikodigital.io"
    const acess = `/Posicao`
    const aux = url + acess
    console.log(aux)
    try {
        const settings = {
            method: 'GET',
            headers: new Headers({
                'Authorization': 'Bearer 0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e',
                'Content-Type': 'application/json'
            })
        }

        const fetchResponse = await fetch(aux, settings)
        const data = await fetchResponse.json()
        console.log(data)
        return data
    }
    catch (error) {
        console.log(error)
    }

}