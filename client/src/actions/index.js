import axios from 'axios'

const ROOT_URL = `http://localhost:8081/tablebase`

export const SEARCH_TABLE = 'SEARCH_TABLE'
export const LOAD_TABLE = 'LOAD_TABLE'

export function searchTable (term) {
  const url = `${ROOT_URL}/search?keyword=${term}`
  const request = axios.get(url)

  return {
    type: SEARCH_TABLE,
    payload: request
  }
}

export function loadTable (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/load`
  const request = axios.get(url)

  return {
    type: LOAD_TABLE,
    payload: request
  }
}
