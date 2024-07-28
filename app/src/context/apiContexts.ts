import axios from "axios"

const API_URL = process.env.PUBLIC_API_URL as string;

if(!API_URL){
    throw new Error("falta a url da api");
}


export async function getLine(keyWord: string){

    if(!keyWord){
        throw new Error("falta o texto para ser procurado");
    }

    try {
        const response = await axios.get(`${API_URL}/linhas=${keyWord}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching lines:', error);
        throw error;
    }
}  