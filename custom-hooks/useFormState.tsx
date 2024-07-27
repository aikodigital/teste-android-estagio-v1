import { useState } from 'react';
import useOnChangeTimeout from './useOnChangeTimeout';

const useFormState = () => {
  const [formState, setFormState] = useState({ loading: false, error: '' });
  const { runTimeout } = useOnChangeTimeout();

  const pesquisarNaApi = async <T,>(
    conclusao: (json: T) => Promise<any>,
    url: string,
    options?: RequestInit
  ) => {
    try {
      const res = await fetch(url, options);
      if (!res.ok) throw new Error('Houve um erro ao pesquisar.');
      const json = (await res.json()) as T;
      if (!json) throw new Error(`Houve um erro ao pesquisar.`);
      if (Array.isArray(json) && !json.length)
        throw new Error('Nenhum resultado encontrado.');
      await conclusao(json);
    } catch (error) {
      if (error instanceof Error) {
        setFormState((prev) => ({ ...prev, error: error.message }));
      }
    } finally {
      setFormState((prev) => ({ ...prev, loading: false }));
    }
  };

  const pesquisar = async <T,>(
    conclusao: (json: T) => Promise<any>,
    url: string,
    options?: RequestInit
  ) => {
    setFormState({ error: '', loading: true });
    runTimeout(() => pesquisarNaApi<T>(conclusao, url, options));
  };

  return { formState, setFormState, pesquisar };
};

export default useFormState;
