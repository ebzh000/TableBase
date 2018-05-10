import {CHANGE_FORM, SENDING_REQUEST, SET_AUTH, SET_ERROR_MESSAGE} from '../actions/types';
import auth from '../controllers/auth';
// Object.assign is not yet fully supported in all browsers, so we fallback to
// a polyfill
const assign = Object.assign || require('object.assign');

// The initial application state
const initialState = {
    formState: {
        username: '',
        password: ''
    },
    currentlySending: false,
    loggedIn: auth.loggedIn(),
    errorMessage: ''
};

// Takes care of changing the application state
export default function loginReducer(state = initialState, action) {
    switch (action.type) {
        case CHANGE_FORM:
            return assign({}, state, {
                formState: action.newState
            });
            break;
        case SET_AUTH:
            return assign({}, state, {
                loggedIn: action.newState
            });
            break;
        case SENDING_REQUEST:
            return assign({}, state, {
                currentlySending: action.sending
            });
            break;
        case SET_ERROR_MESSAGE:
            return assign({}, state, {
                errorMessage: action.message
            });
        default:
            return state;
    }
}