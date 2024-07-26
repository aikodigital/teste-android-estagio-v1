import styled from "styled-components/native";
import { FontAwesome5 } from '@expo/vector-icons';

export type TextTypeProps = 'PRIMARY' | 'SECUNDARY';

type Props = {
    type?: TextTypeProps;
}


export const Container = styled.View`
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
`;

export const Icon = styled(FontAwesome5).attrs<Props>(({theme, type}) => ({
    color: type === 'PRIMARY' ? theme.COLORS.GRAY_600 : theme.COLORS.WHITE,
    size: theme.FONTE_SIZE.LG
}))``


export const Title = styled.Text`
    font-family: ${({theme}) => theme.FONT_FAMILY.BOLD};
    font-size: ${({theme}) => theme.FONTE_SIZE.MD}px;
    color: ${({theme}) => theme.COLORS.GRAY_600};
    margin-bottom: 10px;
`;

export const SubTitle = styled.Text<Props>`
    font-family: ${({theme}) => theme.FONT_FAMILY.REGULAR};
    font-size: ${({theme}) => theme.FONTE_SIZE.SM}px;
    color: ${({theme, type}) => type === 'PRIMARY' ? theme.COLORS.TEAL_600 : theme.COLORS.GRAY_600};
`;

export const ContentIcon = styled.View`
    min-width: 80px;
    flex-direction: row;
    background-color: ${({theme}) => theme.COLORS.PURPLE_600};
    align-items: center;
    justify-content: center;
    padding: 10px;
    border-radius: 8px;
`;

export const TextIcon = styled.Text`
    font-family: ${({theme}) => theme.FONT_FAMILY.REGULAR};
    font-size: ${({theme}) => theme.FONTE_SIZE.SM}px;
    color: ${({theme}) => theme.COLORS.WHITE};
    margin-left: 10px;
`;

export const ContentItems = styled.View`
    justify-content: center;
    align-items: center;
`;