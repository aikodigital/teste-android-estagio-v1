import {TextInput} from 'react-native';
import {linestyles} from '../styles/LinesScreen';

type SearchBarProps = {
  value: string;
  onChange: (newValue: string) => void;
};

 const SearchBar: React.FC<SearchBarProps> = ({value, onChange}) => {
  return (
    <TextInput
      style={linestyles.searchBar}
      placeholder="Buscar linha por código ou nome..."
      value={value}
      onChangeText={(value: string) => {
        onChange(value);
      }}
      placeholderTextColor={"gray"}
    />
  );
};
export default SearchBar