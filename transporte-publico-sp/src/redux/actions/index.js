// src/redux/actions/index.js
import api from '../../services/api';

export const fetchPositionsRequest = () => ({
  type: 'FETCH_POSITIONS_REQUEST',
});

export const fetchPositionsSuccess = (positions) => ({
  type: 'FETCH_POSITIONS_SUCCESS',
  payload: positions,
});

export const fetchPositionsFailure = (error) => ({
  type: 'FETCH_POSITIONS_FAILURE',
  payload: error,
});

export const fetchVehiclePositions = () => async (dispatch) => {
  dispatch(fetchPositionsRequest());
  try {
    const response = await api.getVehiclePositions();
    dispatch(fetchPositionsSuccess(response.data));
  } catch (error) {
    dispatch(fetchPositionsFailure(error));
  }
};

export const searchLinesRequest = () => ({
  type: 'SEARCH_LINES_REQUEST',
});

export const searchLinesSuccess = (lines) => ({
  type: 'SEARCH_LINES_SUCCESS',
  payload: lines,
});

export const searchLinesFailure = (error) => ({
  type: 'SEARCH_LINES_FAILURE',
  payload: error,
});

export const searchLines = (query) => async (dispatch) => {
  dispatch(searchLinesRequest());
  try {
    const response = await api.searchLines(query);
    dispatch(searchLinesSuccess(response.data));
  } catch (error) {
    dispatch(searchLinesFailure(error));
  }
};

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

export const fetchStops = () => async (dispatch) => {
  dispatch(fetchStopsRequest());
  try {
    const response = await api.getStops();
    dispatch(fetchStopsSuccess(response.data));
  } catch (error) {
    dispatch(fetchStopsFailure(error));
  }
};
