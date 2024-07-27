import React, { memo } from 'react';
import InputDeBusca from '../linhaDeOnibus/InputDeBusca';

type Props = {
  inputDePesquisa: {
    codigoParada: string;
    codigoLinha: string;
  };
  setInputDePesquisa: (
    value: React.SetStateAction<{
      codigoParada: string;
      codigoLinha: string;
    }>
  ) => void;
  lidarComPesquisa: () => Promise<void>;
};

const Inputs = ({
  inputDePesquisa,
  setInputDePesquisa,
  lidarComPesquisa,
}: Props) => {
  return (
    <>
      <InputDeBusca
        placeholder='Parada - Ex: 340015329'
        value={inputDePesquisa.codigoParada}
        onChangeText={(v) =>
          setInputDePesquisa((p) => ({ ...p, codigoParada: v }))
        }
        onChange={lidarComPesquisa}
      />
      <InputDeBusca
        placeholder='Linha - Ex: 1989'
        value={inputDePesquisa.codigoLinha}
        onChangeText={(v) =>
          setInputDePesquisa((p) => ({ ...p, codigoLinha: v }))
        }
        onChange={lidarComPesquisa}
      />
    </>
  );
};

export default memo(Inputs);
