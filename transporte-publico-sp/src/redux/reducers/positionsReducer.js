const initialState = {
    data: [],
    loading: false,
    error: null,
  };
  
  const positionsReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'FETCH_POSITIONS_REQUEST':
        return {
          ...state,
          loading: true,
        };
      case 'FETCH_POSITIONS_SUCCESS':
        return {
          ...state,
          loading: false,
          data: action.payload,
        };
      case 'FETCH_POSITIONS_FAILURE':
        return {
          ...state,
          loading: false,
          error: action.payload,
        };
      default:
        return state;
    }
  };
  
  export default positionsReducer;
  