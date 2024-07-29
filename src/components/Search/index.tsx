import React from 'react';
import { Container, ContentIcon, Icon, Input } from "./styles";

type SearchProps = {
  textInput?: string;
  onSearch?: () => void;
  onChangeText?: (text: string) => void;
}

export function Search({ onSearch, textInput, onChangeText }: SearchProps) {
  return (
    <Container>
      <Input
        placeholder="Para onde você quer ir?"
        value={textInput}
        onChangeText={onChangeText}
      />
      <ContentIcon onPress={onSearch}>
        <Icon name="search" /> 
      </ContentIcon>
    </Container>
  )
}
