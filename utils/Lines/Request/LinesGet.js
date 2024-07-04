import { Url } from '../../GlobalVal'

export default PositionAllGet = async (terms) => {
    const respostaGet = await fetch(`${Url}/Linha/Buscar?termosBusca=${terms}`);
    if (respostaGet.status === 200) return await respostaGet.json();
    return false
};