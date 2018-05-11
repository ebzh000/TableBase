import { LOAD_TABLE, CREATE_TABLE } from '../actions/table'

const initState = {
  tableId: 0,
  tableName: 'No Table Yet',
  tags: 'No Tags'
}

export default function (state = initState, action) {
  switch (action.type) {
    case CREATE_TABLE:
      return aciton.payload.data
    case LOAD_TABLE:
      return action.payload.data
  }
  return state
}