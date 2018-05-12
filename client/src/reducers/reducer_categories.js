import { LOAD_CATEGORIES } from '../actions/category'

export default function (state = [], action) {
  switch (action.type) {
    case LOAD_CATEGORIES:
      // const nextState = state
      // action.payload.data.forEach(category => {
      //   nextState[category.categoryId] = category
      // })
      const nextState = action.payload.data
      return nextState
  }
  return state
}
