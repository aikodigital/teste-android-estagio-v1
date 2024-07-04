import { Url } from '../../GlobalVal'

export default StopGet = async (StopCode) => {
    const respostaGet = await fetch(`${Url}/Parada/Buscar?termosBusca=${StopCode}`);
    if (respostaGet.status === 200) return await respostaGet.json();
    return false
};