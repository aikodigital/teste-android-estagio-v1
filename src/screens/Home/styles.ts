import { SafeAreaView } from "react-native-safe-area-context";
import styled from "styled-components/native";

export const Container = styled(SafeAreaView)`
    flex: 1;
    padding: 20px;
    background-color: ${({theme}) => theme.COLORS.WHITE};
`;

export const Title = styled.Text`
    font-family: ${({theme}) => theme.FONT_FAMILY.BOLD};
    font-size: ${({theme}) => theme.FONTE_SIZE.MD}px;
    color: ${({theme}) => theme.COLORS.GRAY_600};
    margin-bottom: 20px;
`;