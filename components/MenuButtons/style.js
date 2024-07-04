import { StyleSheet } from 'react-native';
import { colors } from '../../utils/GlobalVal';
import { width } from '../../utils/GlobalVal';

export const style = StyleSheet.create({
    container: {
        backgroundColor: colors.white,
        borderRadius: 5,
        padding: 6,
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: width * 0.3,
        width: width * 0.3
    },
    img: {
        marginBottom: width * 0.01,
    },
    text: {
        fontWeight: 'bold',
        textAlign: 'center'
    }
});