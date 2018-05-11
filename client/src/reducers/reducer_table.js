import { LOAD_TABLE } from '../actions/index'

const initState = {
  tableId: 0,
  tableName: 'No Table Yet',
  tags: 'No Tags'
}

export default function (state = initState, action) {
  switch (action.type) {
    case LOAD_TABLE:
      return action.payload.data
  }
  return state
}
