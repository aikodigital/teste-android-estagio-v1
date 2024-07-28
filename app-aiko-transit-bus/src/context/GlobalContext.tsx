import React from 'react';

import axios from 'axios';

import { createContext, ReactNode, useState } from "react";

export const GlobalContext = createContext<any>(null);

type GlobalContextType = {
  children: ReactNode;
};

export const GlobalContextProvider: React.FC<GlobalContextType> = ({
  children,
}): any => {
  const [showMenu, setShowMenu] = useState<boolean>(false);
  const [rowOrSearch, setRowOrSearch] = useState<string>("");
  const [infoParada, setInfoParada] = useState<boolean>(false)

  const [dataParada, setDataParada] = useState<[]>([]);
  const [paradaAtualData, setParadaAtualData] = useState<any>([]);
  const [isLinhaSelected, setIsLinhaSelected] = useState<boolean>(false)
  const [showBusInMap, setShowBusInMap] = useState<boolean>(false)
  const [showParadasBusSelected, setShowParadasBusSelected] = useState<boolean>(false)

  const filterParada = async (cp: string)=> {

    const dataParadaAtt: any = dataParada.filter((cpParada: any)=> cpParada.cp === cp)
    const cpParada = dataParadaAtt[0].cp

    const response = await axios.get(`https://api.olhovivo.sptrans.com.br/v2.1/Previsao/Parada?codigoParada=${cpParada}`)
    setParadaAtualData(response.data)

  }

  const [dataLinha, setDataLinha] = useState<any>()
  const getLinha = (id: string)=> {

    const filterLinha = paradaAtualData?.p?.l.filter((parada: any)=> parada.cl === id)
    setDataLinha(filterLinha)
   
  }

  return (
    <GlobalContext.Provider
      value={{
        showMenu,
        rowOrSearch,
        infoParada,
        dataParada,
        paradaAtualData,
        isLinhaSelected,
        dataLinha,
        showBusInMap,
        showParadasBusSelected,
        setShowMenu,
        setRowOrSearch,
        setInfoParada,
        setDataParada,
        filterParada,
        setIsLinhaSelected,
        getLinha,
        setDataLinha,
        setShowBusInMap,
        setShowParadasBusSelected
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
