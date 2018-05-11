import { combineReducers } from 'redux'
import TableListReducer from './reducer_table_list'
import TableHtmlReducer from './reducer_table_html'
import TableReducer from './reducer_table'
import CategoryReducer from './reducer_categories'

const rootReducer = combineReducers({
  tableList: TableListReducer,
  tableHtml: TableHtmlReducer,
  table: TableReducer,
  categories: CategoryReducer
})

export default rootReducer
