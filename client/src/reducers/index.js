import { combineReducers } from 'redux'
import TableListReducer from './reducer_table_list'
import TableHtmlReducer from './reducer_table_html'
import TableReducer from './reducer_table'
import CategoryReducer from './reducer_categories'
import CategoryNoRootReducer from './reducerCategoriesNoRoot'
import RootCategoryReducer from './reducerRootCategories'

const rootReducer = combineReducers({
  tableList: TableListReducer,
  tableHtml: TableHtmlReducer,
  table: TableReducer,
  categories: CategoryReducer,
  categoriesNoRoot: CategoryNoRootReducer,
  rootCategories: RootCategoryReducer
})

export default rootReducer
