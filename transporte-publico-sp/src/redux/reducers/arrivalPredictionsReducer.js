// src/redux/reducers/arrivalPredictionsReducer.js
const initialState = {
    data: [],
    loading: false,
    error: null,
  };
  
  const arrivalPredictionsReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'FETCH_ARRIVAL_PREDICTIONS_REQUEST':
        return { ...state, loading: true };
      case 'FETCH_ARRIVAL_PREDICTIONS_SUCCESS':
        return { ...state, loading: false, data: action.payload };
      case 'FETCH_ARRIVAL_PREDICTIONS_FAILURE':
        return { ...state, loading: false, error: action.payload };
      default:
        return state;
    }
  };
  
  export default arrivalPredictionsReducer;
  