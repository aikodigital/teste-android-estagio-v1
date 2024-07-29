import { TextInput, TouchableOpacity } from "react-native";
import styled from "styled-components/native";
import { FontAwesome5 } from '@expo/vector-icons';

export const Container = styled.View`
    max-height: 40px;
    min-height: 40px;
    flex-direction: row;
    border-radius: 8px;
    align-items: center;
    background-color: ${({theme}) => theme.COLORS.GRAY_300};
    margin-bottom: 20px;
`;

export const Input = styled(TextInput).attrs(({theme}) => ({
    placeholderTextColor: theme.COLORS.GRAY_400
}))`
    flex: 1;
    max-height: 40px;
    min-height: 40px;
    padding: 10px;
`;


export const ContentIcon = styled(TouchableOpacity)`
    max-height: 40px;
    min-height: 40px;
    background-color: ${({theme}) => theme.COLORS.PURPLE_600};
    align-items: center;
    justify-content: center;
    padding: 10px;
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
`;

export const Icon = styled(FontAwesome5).attrs(({theme}) => ({
    color: theme.COLORS.WHITE,
    size: theme.FONTE_SIZE.LG
}))``