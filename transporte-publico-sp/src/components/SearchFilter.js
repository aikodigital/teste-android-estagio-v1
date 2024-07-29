import React, { useState } from 'react';
import { useDispatch } from 'react-redux'; // Corrigido: importação do react-redux
import { searchLines } from '../redux/actions'; // Corrigido: caminho relativo para actions

const SearchFilter = () => {
  const [query, setQuery] = useState('');
  const dispatch = useDispatch();

  const handleSearch = () => {
    dispatch(searchLines(query));
  };

  return (
    <div>
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Pesquisar linhas..."
      />
      <button onClick={handleSearch}>Buscar</button>
    </div>
  );
};

export default SearchFilter;