import {combineReducers} from 'redux';
import LoginReducer from './LoginReducer';

const rootReducer = combineReducers({
    loginReducer: LoginReducer
})

export default rootReducer;