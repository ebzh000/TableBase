import { SEARCH_TABLE } from '../actions/table'

export default function (state = [], action) {
  switch (action.type) {
    case SEARCH_TABLE:
      return action.payload.data
  }
  return state
}
