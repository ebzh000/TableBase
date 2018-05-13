import { LOAD_CATEGORIES_NO_ROOT } from '../actions/category'

export default function (state = [], action) {
  switch (action.type) {
    case LOAD_CATEGORIES_NO_ROOT:
      return action.payload.data
  }
  return state
}
