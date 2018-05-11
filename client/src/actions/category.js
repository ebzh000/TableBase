import axios from 'axios'

export const LOAD_CATEGORIES = 'LOAD_CATEGORIES'
export const CREATE_TOP_LEVEL_CATEGORY = 'CREATE_TOP_LEVEL_CATEGORY'
export const CREATE_CATEGORY = 'CREATE_CATEGORY'
export const UPDATE_CATEGORY = 'UPDATE_CATEGORY'
export const DELETE_CATEGORY = 'DELETE_CATEGORY'
export const DUPLICATE_CATEGORY = 'DUPLICATE_CATEGORY'
export const SPLIT_CATEGORY = 'SPLIT_CATEGORY'
export const COMBINE_CATEGORY = 'COMBINE_CATEGORY'
export const DELETE_TOP_LEVEL_CATEGORY = 'DELETE_TOP_LEVEL_CATEGORY'

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
  const url = `${ROOT_URL}/table/${tableId}/createTopLevelCategory?toHtml=true`
}

export function createCategory (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/createCategory?toHtml=true`
}

export function updateCategory (tableId, categoryId) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?toHtml=true`
}

export function deleteCategory (tableId, categoryId) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?toHtml=true`
}

export function duplicateCategory (tableId, categoryId) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?toHtml=true`
}

export function splitCategory (tableId, categoryId) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?toHtml=true`
}

export function combineCategory (tableId, categoryId) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?toHtml=true`
}