import axios from "axios";

export async function postAuth() {
  await axios.post(
    `https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=${process.env.TOKEN}`
  );
}
