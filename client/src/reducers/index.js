import { combineReducers } from 'redux'
import TableListReducer from './reducer_table_list'
import TableHtmlReducer from './reducer_table_html'
import TableReducer from './reducer_table'

const rootReducer = combineReducers({
  tableList: TableListReducer,
  tableHtml: TableHtmlReducer,
  table: TableReducer
})

export default rootReducer
