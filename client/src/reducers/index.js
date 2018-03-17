import { combineReducers } from 'redux';
import LoginReducer from './reducer_login';

const rootReducer = combineReducers({
  loggedIn: LoginReducer
});

export default rootReducer;
