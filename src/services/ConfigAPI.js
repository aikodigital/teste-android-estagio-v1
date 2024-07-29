import axios from 'axios';

const ConfigAPI = axios.create({
  baseURL: 'https://aiko-olhovivo-proxy.aikodigital.io/'
});

export default ConfigAPI;