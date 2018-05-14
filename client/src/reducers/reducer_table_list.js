import { SEARCH_TABLE, DELETE_TABLE } from '../actions/table'

export default function (state = [], action) {
  switch (action.type) {
    case SEARCH_TABLE:
      return action.payload.data
    case DELETE_TABLE:
      const nextState = state.filter((table) => table.tableId != action.payload)
      return nextState
  }
  return state
}
