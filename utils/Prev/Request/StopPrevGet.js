import { Url } from '../../GlobalVal'

export default StopPrevGet = async (StopCad) => {
    const respostaGet = await fetch(`${Url}/Previsao/Parada?codigoParada=${StopCad}`);
    if (respostaGet.status === 200) return await respostaGet.json();
    return false
};