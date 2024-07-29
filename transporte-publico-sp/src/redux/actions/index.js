// src/redux/actions/index.js
import axios from 'axios';

// Actions for fetching vehicle positions
export const fetchVehiclePositionsRequest = () => ({
  type: 'FETCH_VEHICLE_POSITIONS_REQUEST',
});

export const fetchVehiclePositionsSuccess = (positions) => ({
  type: 'FETCH_VEHICLE_POSITIONS_SUCCESS',
  payload: positions,
});

export const fetchVehiclePositionsFailure = (error) => ({
  type: 'FETCH_VEHICLE_POSITIONS_FAILURE',
  payload: error,
});

export const fetchVehiclePositions = () => {
  return async (dispatch) => {
    dispatch(fetchVehiclePositionsRequest());
    try {
      const response = await axios.get('YOUR_API_ENDPOINT/vehicle_positions');
      dispatch(fetchVehiclePositionsSuccess(response.data));
    } catch (error) {
      dispatch(fetchVehiclePositionsFailure(error.message));
    }
  };
};

// Actions for fetching bus lines
export const fetchBusLinesRequest = () => ({
  type: 'FETCH_BUS_LINES_REQUEST',
});

export const fetchBusLinesSuccess = (lines) => ({
  type: 'FETCH_BUS_LINES_SUCCESS',
  payload: lines,
});

export const fetchBusLinesFailure = (error) => ({
  type: 'FETCH_BUS_LINES_FAILURE',
  payload: error,
});

export const fetchBusLines = () => {
  return async (dispatch) => {
    dispatch(fetchBusLinesRequest());
    try {
      const response = await axios.get('YOUR_API_ENDPOINT/bus_lines');
      dispatch(fetchBusLinesSuccess(response.data));
    } catch (error) {
      dispatch(fetchBusLinesFailure(error.message));
    }
  };
};

// Actions for fetching stops
export const fetchStopsRequest = () => ({
  type: 'FETCH_STOPS_REQUEST',
});

export const fetchStopsSuccess = (stops) => ({
  type: 'FETCH_STOPS_SUCCESS',
  payload: stops,
});

export const fetchStopsFailure = (error) => ({
  type: 'FETCH_STOPS_FAILURE',
  payload: error,
});

export const fetchStops = () => {
  return async (dispatch) => {
    dispatch(fetchStopsRequest());
    try {
      const response = await axios.get('YOUR_API_ENDPOINT/stops');
      dispatch(fetchStopsSuccess(response.data));
    } catch (error) {
      dispatch(fetchStopsFailure(error.message));
    }
  };
};

// Actions for fetching arrival predictions
export const fetchArrivalPredictionsRequest = () => ({
  type: 'FETCH_ARRIVAL_PREDICTIONS_REQUEST',
});

export const fetchArrivalPredictionsSuccess = (predictions) => ({
  type: 'FETCH_ARRIVAL_PREDICTIONS_SUCCESS',
  payload: predictions,
});

export const fetchArrivalPredictionsFailure = (error) => ({
  type: 'FETCH_ARRIVAL_PREDICTIONS_FAILURE',
  payload: error,
});

export const fetchArrivalPredictions = (stopId) => {
  return async (dispatch) => {
    dispatch(fetchArrivalPredictionsRequest());
    try {
      const response = await axios.get(`YOUR_API_ENDPOINT/arrival_predictions/${stopId}`);
      dispatch(fetchArrivalPredictionsSuccess(response.data));
    } catch (error) {
      dispatch(fetchArrivalPredictionsFailure(error.message));
    }
  };
};

// Action for searching bus lines
export const searchLines = (query) => {
  return async (dispatch) => {
    try {
      const response = await axios.get(`YOUR_API_ENDPOINT/bus_lines?query=${query}`);
      dispatch(fetchBusLinesSuccess(response.data));
    } catch (error) {
      dispatch(fetchBusLinesFailure(error.message));
    }
  };
};
