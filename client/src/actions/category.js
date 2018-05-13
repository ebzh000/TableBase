import axios from 'axios'

export const LOAD_CATEGORIES = 'LOAD_CATEGORIES'
export const LOAD_CATEGORIES_NO_ROOT = 'LOAD_CATEGORIES_NO_ROOT'
export const LOAD_ROOT_CATEGORIES = 'LOAD_ROOT_CATEGORIES'
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
  const url = `${ROOT_URL}/table/${tableId}/categories?excludeRoot=false`
  const request = axios.get(url)

  return {
    type: LOAD_CATEGORIES,
    payload: request
  }
}

export function loadCategoriesNoRoot (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/categories?excludeRoot=true`
  const request = axios.get(url)

  return {
    type: LOAD_CATEGORIES_NO_ROOT,
    payload: request
  }
}

export function loadRootCategories (tableId) {
  const url = `${ROOT_URL}/table/${tableId}/rootCategories`
  const request = axios.get(url)

  return {
    type: LOAD_ROOT_CATEGORIES,
    payload: request
  }
}

export function createTopLevelCategory (tableId, categoryName) {
  const url = `${ROOT_URL}/table/${tableId}/category/createTopLevelCategory?toHtml=true`
  const body = {
    attributeName: categoryName
  }

  const request = axios.post(url, body)

  return {
    type: CREATE_TOP_LEVEL_CATEGORY,
    payload: request
  }
}

export function createCategory (tableId, categoryName, parentId, linkChildren) {
  const url = `${ROOT_URL}/table/${tableId}/category/create?toHtml=true`
  const body = {
    attributeName: categoryName,
    parentId: parentId,
    linkChildren: linkChildren
  }
  console.log(body)
  const request = axios.post(url, body)

  return {
    type: CREATE_CATEGORY,
    payload: request
  }
}

export function updateCategory (tableId, categoryId, categoryName, parentId) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?toHtml=true`
  const body = {
    attributeName: categoryName,
    parentId: parentId
  }

  const request = axios.post(url, body)

  return {
    type: UPDATE_CATEGORY,
    payload: request
  }
}

export function deleteCategory (tableId, categoryId, deleteChildren) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}?deleteChildren=${deleteChildren}&toHtml=true`
  console.log(url)
  const request = axios.delete(url)

  return {
    type: DELETE_CATEGORY,
    payload: request
  }
}

export function duplicateCategory (tableId, categoryId) {
  const url = `${ROOT_URL}/table/${tableId}/category/duplicate/${categoryId}?toHtml=true`
  const request = axios.post(url)

  return {
    type: DUPLICATE_CATEGORY,
    payload: request
  }
}

export function splitCategory (tableId, categoryId, newCategoryName, dataOperationType, threshold) {
  const url = `${ROOT_URL}/table/${tableId}/category/split/${categoryId}?toHtml=true`
  const body = {
    newCategoryName: newCategoryName,
    dataOperationType: dataOperationType,
    threshold: threshold
  }
  const request = axios.post(url, body)

  return {
    type: SPLIT_CATEGORY,
    payload: request
  }
}

export function combineCategory (tableId, categoryId1, categoryId2, newCategoryName, dataOperationType) {
  const url = `${ROOT_URL}/table/${tableId}/category/combine?toHtml=true`
  const body = {
    categoryId1: categoryId1,
    categoryId2: categoryId2,
    newCategoryName: newCategoryName,
    dataOperationType: dataOperationType
  }
  const request = axios.post(url, body)

  return {
    type: COMBINE_CATEGORY,
    payload: request
  }
}

export function deleteTopLevelCategory (tableId, categoryId, dataOperationType) {
  const url = `${ROOT_URL}/table/${tableId}/category/${categoryId}/deleteTopLevelCategory?toHtml=true`
  const body = {
    dataOperationType: dataOperationType
  }

  const request = axios({method: 'delete', url: url, data: body})

  return {
    type: DELETE_TOP_LEVEL_CATEGORY,
    payload: request
  }
}
