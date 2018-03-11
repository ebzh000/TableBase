import axios from 'axios'
import Actions from 'react-router'
import {
  LOGIN_USER_SUCCESS,
  LOGIN_USER_FAIL,
  LOGIN_USER
} from './types';
const ROOT_URL = `http://localhost:8081`;

export function login (email, password) {
  const url = `${ROOT_URL}/user/`;
  const request = axios.get(url, {
    params: {
      email: email,
      password: password
    }
  });

  console.log("login");
  console.log(request);

  return {
    type: LOGIN,
    payload: request
  }
}

const loginUserFailed = dispatch => {
  dispatch({ type: LOGIN_USER_FAIL })
};

const loginUserSuccess = (dispatch, user) => {
  dispatch({
    type: LOGIN_USER_SUCCESS,
    payload: user
  })
};
