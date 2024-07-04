import { Url } from '../../GlobalVal'

export default StopLinesGet = async (LineCod) => {
    const respostaGet = await fetch(`${Url}/Parada/BuscarParadasPorLinha?codigoLinha=${LineCod}`);
    if (respostaGet.status === 200) return await respostaGet.json();
    return false
};