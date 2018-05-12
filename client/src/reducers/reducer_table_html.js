import { LOAD_TABLE_HTML } from '../actions/table'
import { CREATE_TOP_LEVEL_CATEGORY, CREATE_CATEGORY, UPDATE_CATEGORY, DELETE_CATEGORY, DUPLICATE_CATEGORY, SPLIT_CATEGORY, COMBINE_CATEGORY, DELETE_TOP_LEVEL_CATEGORY } from '../actions/category'
import { UPDATE_ENTRY } from '../actions/entry'

const initHtml = '<h2 class=\'no-table-found\'>NO TABLE FOUND</h2>'
const erroredTable = '<h2 class=\'no-table-found\'>Failed to generate table</h2>'

export default function (state = initHtml, action) {
  switch (action.type) {
    case LOAD_TABLE_HTML:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case CREATE_TOP_LEVEL_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case CREATE_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case UPDATE_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case DELETE_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case DUPLICATE_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case SPLIT_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case COMBINE_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case DELETE_TOP_LEVEL_CATEGORY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
    case UPDATE_ENTRY:
      if (action.payload.data === undefined) {
        return erroredTable
      } else {
        return action.payload.data
      }
  }
  return state
}
