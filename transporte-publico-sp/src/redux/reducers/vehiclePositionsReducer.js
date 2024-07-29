// src/redux/reducers/vehiclePositionsReducer.js
const initialState = {
    data: [],
    loading: false,
    error: null,
  };
  
  const vehiclePositionsReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'FETCH_VEHICLE_POSITIONS_REQUEST':
        return { ...state, loading: true };
      case 'FETCH_VEHICLE_POSITIONS_SUCCESS':
        return { ...state, loading: false, data: action.payload };
      case 'FETCH_VEHICLE_POSITIONS_FAILURE':
        return { ...state, loading: false, error: action.payload };
      default:
        return state;
    }
  };
  
  export default vehiclePositionsReducer;
  