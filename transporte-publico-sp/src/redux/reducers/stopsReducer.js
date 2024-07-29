const initialState = {
  data: [], // Certifique-se de que este é um array
  loading: false,
  error: null,
};

const stopsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'FETCH_STOPS_REQUEST':
      return {
        ...state,
        loading: true,
        error: null,
      };
    case 'FETCH_STOPS_SUCCESS':
      return {
        ...state,
        loading: false,
        data: action.payload,
      };
    case 'FETCH_STOPS_FAILURE':
      return {
        ...state,
        loading: false,
        error: action.payload,
      };
    default:
      return state;
  }
};

export default stopsReducer;
