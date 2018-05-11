import { LOAD_TABLE_HTML } from '../actions/index'

export default function (state = '---', action) {
  switch (action.type) {
    case LOAD_TABLE_HTML:
      return action.payload.data
  }
  return state
}
