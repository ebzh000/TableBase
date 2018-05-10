import axios from 'axios'

const ROOT_URL = `http://localhost:8081/tablebase`

export const SEARCH_TABLE = 'SEARCH_TABLE'

export function searchTable (term) {
  const url = `${ROOT_URL}/search?keyword=${term}`
  const request = axios.get(url)

  return {
    type: SEARCH_TABLE,
    payload: request
  }
}
