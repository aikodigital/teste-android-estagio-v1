const initialState = {
    data: [],
    loading: false,
    error: null,
  };
  
  const linesReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'SEARCH_LINES_REQUEST':
        return {
          ...state,
          loading: true,
          error: null,
        };
      case 'SEARCH_LINES_SUCCESS':
        return {
          ...state,
          loading: false,
          data: action.payload,
        };
      case 'SEARCH_LINES_FAILURE':
        return {
          ...state,
          loading: false,
          error: action.payload,
        };
      default:
        return state;
    }
  };
  
  export default linesReducer;
  