import StopPrevGet from "./Request/StopPrevGet";
import { ToastAndroid } from 'react-native';

export default StopPrev = async (StopCod) => {
    const resp = await StopPrevGet(StopCod);

    if (resp.p === null || !resp.p) {
        ToastAndroid.showWithGravity(
            "A parada não foi encontrada. Verifique o código e tente novamente.",
            ToastAndroid.LONG,
            ToastAndroid.CENTER
        );
        return false;
    }
    return resp;
};