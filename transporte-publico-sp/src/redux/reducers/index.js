// src/redux/reducers/index.js
import { combineReducers } from 'redux';
import vehiclePositionsReducer from './vehiclePositionsReducer';
import busLinesReducer from './busLinesReducer';
import stopsReducer from './stopsReducer';
import arrivalPredictionsReducer from './arrivalPredictionsReducer';

const rootReducer = combineReducers({
  vehiclePositions: vehiclePositionsReducer,
  busLines: busLinesReducer,
  stops: stopsReducer,
  arrivalPredictions: arrivalPredictionsReducer,
});

export default rootReducer;
