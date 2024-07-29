import axios from 'axios';

const ConfigAPI = axios.create({
  baseURL: 'https://aiko-olhovivo-proxy.aikodigital.io/', //Proxy fornecida pela Aiko
  //baseURL_old: 'http://api.olhovivo.sptrans.com.br/v2.1' //Nao estava funcionando no momento da implementacao
});

export default ConfigAPI;