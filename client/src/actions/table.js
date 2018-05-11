import axios from 'axios'
import { SEARCH_TABLE, LOAD_TABLE, LOAD_TABLE_HTML } from './constants'

const ROOT_URL = `http://localhost:8081/tablebase`

export function searchTable (term) {
  const url = `${ROOT_URL}/search?keyword=${term}`
  const request = axios.get(url)
  console.log('loading shit')
  console.log(request)

  return {
    type: SEARCH_TABLE,
    payload: request
  }
}

export function loadTableHtml (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/html`
  const request = axios.get(url)
  console.log('shit is fucked')
  console.log(request)

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
