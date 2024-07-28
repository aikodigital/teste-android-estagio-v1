import axios from 'axios';

export async function getLine(keyWord: string) {
    if (!keyWord) {
        throw new Error("falta o texto para ser procurado");
    }

    try {
        const response = await axios.get(`http://localhost:3000/linhas`, {
            params: { termosBusca: keyWord }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching lines:', error);
        throw error;
    }
}
