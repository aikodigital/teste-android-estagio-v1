import axios from 'axios';


const API_URL = 'http://192.168.0.104:3000';
export async function getLine(keyWord: string) {
    if (!keyWord) {
        throw new Error("falta o texto para ser procurado");
    }

    try {
        const response = await axios.get(`${API_URL}/linhas?termosBusca=${keyWord}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching lines:', error);
        throw error;
    }
}