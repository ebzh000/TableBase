import { LOAD_CATEGORIES } from '../actions/constants'

export default function (state = [], action) {
  switch (action.type) {
    case LOAD_CATEGORIES:
      return action.payload.data
  }
  return state
}
