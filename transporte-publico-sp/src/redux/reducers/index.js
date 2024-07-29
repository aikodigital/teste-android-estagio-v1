import { combineReducers } from 'redux';
import positionsReducer from './positionsReducer';
import linesReducer from './linesReducer';
import stopsReducer from './stopsReducer';

const rootReducer = combineReducers({
  positions: positionsReducer,
  lines: linesReducer,
  stops: stopsReducer,
});

export default rootReducer;
