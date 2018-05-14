import axios from 'axios'

export const UPDATE_ENTRY = 'UPDATE_ENTRY'

const ROOT_URL = `http://localhost:8081/tablebase`

export function updateEntry (tableId, entryId, label) {
  const url = `${ROOT_URL}/table/${tableId}/entry/${entryId}?toHtml=true`
  const body = {
    data: label
  }

  console.log(body)
  const request = axios.post(url, body)

  return {
    type: UPDATE_ENTRY,
    payload: request
  }
}
