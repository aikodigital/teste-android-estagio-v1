import LinesGet from "./Request/LinesGet";
import { ToastAndroid } from 'react-native';

export default Lines = async (terms) => {
    const resp = await LinesGet(terms);

    if (resp.length == 0) {
        ToastAndroid.showWithGravity(
            "A linha não foi encontrada. Verifique o código e tente novamente.",
            ToastAndroid.LONG,
            ToastAndroid.CENTER
        );
        return false;
    }
    return resp;
};