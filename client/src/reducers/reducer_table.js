import { SEARCH_TABLE, LOAD_TABLE } from '../actions/index'

export default function (state = [], action) {
  switch (action.type) {
    case SEARCH_TABLE:
      return [action.payload.data, ...state]
    case LOAD_TABLE:
      return [action.payload.data, ...state]
  }
  return state
}
