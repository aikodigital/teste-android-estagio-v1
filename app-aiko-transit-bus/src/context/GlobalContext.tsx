import { createContext, FunctionComponent, ReactNode, useState } from "react";

export const GlobalContext = createContext<any>(null);

type GlobalContextType = {
  children: ReactNode;
};

export const GlobalContextProvider: React.FC<GlobalContextType> = ({
  children,
}): any => {
  const [showMenu, setShowMenu] = useState<boolean>(false);
  const [rowOrSearch, setRowOrSearch] = useState<string>("");

  return (
    <GlobalContext.Provider
      value={{
        showMenu,
        rowOrSearch,
        setShowMenu,
        setRowOrSearch,
      }}
    >
      {children}
    </GlobalContext.Provider>
  );
};
