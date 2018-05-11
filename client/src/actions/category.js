import axios from 'axios'
import { LOAD_CATEGORIES, CREATE_TOP_LEVEL_CATEGORY, CREATE_CATEGORY, UPDATE_CATEGORY, DELETE_CATEGORY, DUPLICATE_CATEGORY, SPLIT_CATEGORY, COMBINE_CATEGORY } from './constants'

const ROOT_URL = `http://localhost:8081/tablebase`

export function loadCategories (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/categories`
  const request = axios.get(url)

  return {
    type: LOAD_CATEGORIES,
    payload: request
  }
}

export function createTopLevelCategory (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/createTopLevelCategory`
}
