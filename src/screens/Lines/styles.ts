import { SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";

export const Container = styled(SafeAreaView)`
    flex: 1;
    background-color: ${({theme}) => theme.COLORS.WHITE};
`;

export const Title = styled.Text`
    font-family: ${({theme}) => theme.FONT_FAMILY.BOLD};
    font-size: ${({theme}) => theme.FONTE_SIZE.MD}px;
    color: ${({theme}) => theme.COLORS.GRAY_600};
    margin-bottom: 10px;
    text-align: center;
    margin-top: 10px;
`;

export const Content = styled.View`
    flex: 1;
    position: relative;
    background-color: red;
    padding: 20px;
    z-index: 999;
`;