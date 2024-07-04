import StopLinesGet from "./Request/StopLinesGet";

export default StopLines = async (LineCod) => {
    return await StopLinesGet(LineCod);
};