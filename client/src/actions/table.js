import axios from 'axios'

export const CREATE_TABLE = 'CREATE_TABLE'
export const SEARCH_TABLE = 'SEARCH_TABLE'
export const LOAD_TABLE_HTML = 'LOAD_TABLE_HTML'
export const LOAD_TABLE = 'LOAD_TABLE'

const ROOT_URL = `http://localhost:8081/tablebase`

export function createTable (tableName, tags, userId, type) {
  const url = `${ROOT_URL}/create`
  const body = {
    userId: userId,
    tableName: tableName,
    tags: tags,
    type: type
  }

  const request = axios.post(url, body)

  return {
    type: CREATE_TABLE,
    payload: request
  }
}

export function searchTable (term) {
  const url = `${ROOT_URL}/search?keyword=${term}`
  const request = axios.get(url)

  return {
    type: SEARCH_TABLE,
    payload: request
  }
}

export function loadTableHtml (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/html`
  const request = axios.get(url)

  return {
    type: LOAD_TABLE_HTML,
    payload: request
  }
}

export function loadTable (tableId) {
  const url = `${ROOT_URL}/table/${tableId}`
  const request = axios.get(url)

  return {
    type: LOAD_TABLE,
    payload: request
  }
}
