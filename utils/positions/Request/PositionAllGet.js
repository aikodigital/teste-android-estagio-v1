import { Url } from '../../GlobalVal'

export default PositionAllGet = async () => {
    const respostaGet = await fetch(`${Url}/Posicao`);
    if (respostaGet.status === 200) return await respostaGet.json();
    return false
};