export const autenticarNaApi = async () => {
  try {
    const res = await fetch(
      String(process.env.API_URL) +
        '/Login/Autenticar?token=' +
        String(process.env.API_TOKEN),
      { method: 'POST' }
    );
    if ((await res.text()) === 'false') throw new Error('Erro ao logar.');
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};
