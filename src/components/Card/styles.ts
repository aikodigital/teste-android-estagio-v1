import styled from "styled-components/native";
import { FontAwesome5 } from '@expo/vector-icons';

export type TextTypeProps = 'PRIMARY' | 'SECUNDARY';

type Props = {
    type?: TextTypeProps;
}

export const Container = styled.View`
    min-height: 300px;
    background-color: ${({theme}) => theme.COLORS.PURPLE_50};
    border-radius: 8px;
`;


export const Header = styled.View`
    flex-direction: row;
    padding: 20px;
    align-items: center;
`;

export const ContentIconHeader = styled.View`
    max-height: 40px;
    min-height: 40px;
    background-color: ${({theme}) => theme.COLORS.PURPLE_600};
    align-items: center;
    justify-content: center;
    padding: 10px;
    border-radius: 50px;
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

export const ContentTextHeader = styled.View`
    padding: 20px;
    justify-content: space-between;
`;


export const ContentMap = styled.View`
    max-height: 250px;
    min-height: 250px;
    background-color: ${({theme}) => theme.COLORS.GRAY_400};
    align-items: center;
    justify-content: center;
`;

export const Content = styled.View`
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
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