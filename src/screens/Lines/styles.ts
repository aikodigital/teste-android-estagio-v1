import { SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";
import { MaterialCommunityIcons } from '@expo/vector-icons';

export type IconTypeProps = 'PRIMARY' | 'SECONDARY';

type Props = {
    type?: IconTypeProps;
}

export const Container = styled(SafeAreaView)`
    flex: 1;
    background-color: ${({ theme }) => theme.COLORS.WHITE};
`;

export const Title = styled.Text`
    font-family: ${({ theme }) => theme.FONT_FAMILY.BOLD};
    font-size: ${({ theme }) => theme.FONTE_SIZE.MD}px;
    color: ${({ theme }) => theme.COLORS.GRAY_600};
    margin-bottom: 10px;
    text-align: center;
    margin-top: 10px;
`;

export const Content = styled.View`
    position: relative;
    z-index: 10;
    padding: 20px;
`;


export const Icon = styled(MaterialCommunityIcons).attrs<Props>(({ theme, type }) => ({
    color: type === 'PRIMARY' ? theme.COLORS.PURPLE_600 : theme.COLORS.TEAL_600,
    size: theme.FONTE_SIZE.SM,
}))<Props>``;