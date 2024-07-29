// src/redux/reducers/busLinesReducer.js
const initialState = {
    data: [],
    loading: false,
    error: null,
  };
  
  const busLinesReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'FETCH_BUS_LINES_REQUEST':
        return { ...state, loading: true };
      case 'FETCH_BUS_LINES_SUCCESS':
        return { ...state, loading: false, data: action.payload };
      case 'FETCH_BUS_LINES_FAILURE':
        return { ...state, loading: false, error: action.payload };
      default:
        return state;
    }
  };
  
  export default busLinesReducer;
  