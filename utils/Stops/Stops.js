import StopGet from "./Request/StopGet";
import { ToastAndroid } from 'react-native';

export default Stops = async (LineCod) => {
    const resp = await StopGet(LineCod);

    if (resp.length == 0) {
        ToastAndroid.showWithGravity(
            "A parada não foi encontrada. Verifique o código e tente novamente.",
            ToastAndroid.LONG,
            ToastAndroid.CENTER
        );
        return false;
    }
    return resp;
};