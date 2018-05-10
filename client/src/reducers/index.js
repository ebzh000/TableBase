import { combineReducers } from 'redux'
import TableReducer from './reducer_table'

const rootReducer = combineReducers({
  table: TableReducer
})

export default rootReducer
