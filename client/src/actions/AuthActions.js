import axios from 'axios'
import {
  LOGIN_USER_SUCCESS,
  LOGIN_USER_FAIL,
  LOGIN_USER
} from './types';

export const userActions = {
  login
};

const ROOT_URL = `http://localhost:8081`;

export const loginUser = ({ email, password }) => {
  return dispatch => {
    dispatch({type: LOGIN_USER});

  }
};

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
