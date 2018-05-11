import { LOAD_TABLE_HTML, CREATE_TOP_LEVEL_CATEGORY, CREATE_CATEGORY, UPDATE_CATEGORY, DELETE_CATEGORY, DUPLICATE_CATEGORY, SPLIT_CATEGORY, COMBINE_CATEGORY } from '../actions/constants'

const initHtml = '<h2 class=\'no-table-found\'>NO TABLE FOUND</h2>'

export default function (state = initHtml, action) {
  switch (action.type) {
    case LOAD_TABLE_HTML:
      return action.payload.data
    case CREATE_TOP_LEVEL_CATEGORY:
      return action.payload.data
    case CREATE_CATEGORY:
      return action.payload.data
    case UPDATE_CATEGORY:
      return action.payload.data
    case DELETE_CATEGORY:
      return action.payload.data
    case DUPLICATE_CATEGORY:
      return action.payload.data
    case SPLIT_CATEGORY:
      return action.payload.data
    case COMBINE_CATEGORY:
      return action.payload.data
  }
  return state
}
