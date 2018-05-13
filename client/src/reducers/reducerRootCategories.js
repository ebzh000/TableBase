import { LOAD_ROOT_CATEGORIES } from '../actions/category'

export default function (state = [], action) {
  switch (action.type) {
    case LOAD_ROOT_CATEGORIES:
      return action.payload.data
  }
  return state
}
