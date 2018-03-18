// Import all the third party stuff
import React from 'react';
import {applyMiddleware, compose, createStore} from 'redux';
import ReduxThunk from 'redux-thunk';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {browserHistory, Route, Router} from 'react-router';
import loginReducer from './reducers/LoginReducer';
import FontFaceObserver from 'fontfaceobserver';

// Import the components used as pages
import HomePage from './containers/HomePage';
import LoginPage from './containers/LoginPage';
import RegisterPage from './containers/RegisterPage';
import Dashboard from './containers/Dashboard';
import NotFound from './containers/NotFoundPage';
import App from './components/App';

// Import the CSS file, which webpack transfers to the build folder
import './styles/main.css';

// When Open Sans is loaded, add the js-open-sans-loaded class to the body
// which swaps out the fonts
const openSansObserver = new FontFaceObserver('Open Sans');

openSansObserver.check().then(() => {
    document.body.classList.add('js-open-sans-loaded');
}, (err) => {
    document.body.classList.remove('js-open-sans-loaded');
});

// Creates the Redux reducer with the redux-thunk middleware, which allows us
// to do asynchronous things in the actions
const createStoreWithMiddleware = compose(applyMiddleware(ReduxThunk))(createStore);
const store = createStoreWithMiddleware(loginReducer);


function checkAuth(nextState, replaceState) {
    let {loggedIn} = store.getState();

    // check if the path isn't dashboard
    // that way we can apply specific logic
    // to display/render the path we want to
    if (nextState.location.pathname !== '/dashboard') {
        if (loggedIn) {
            if (nextState.location.state && nextState.location.pathname) {
                replaceState(null, nextState.location.pathname);
            } else {
                replaceState(null, '/');
            }
        }
    } else {
        // If the user is already logged in, forward them to the homepage
        if (!loggedIn) {
            if (nextState.location.state && nextState.location.pathname) {
                replaceState(null, nextState.location.pathname);
            } else {
                replaceState(null, '/');
            }
        }
    }
}

// Mostly boilerplate, except for the Routes. These are the pages you can go to,
// which are all wrapped in the App component, which contains the navigation etc
ReactDOM.render(
    <Provider store={store}>
        <Router history={browserHistory}>
            <Route component={App}>
                <Route path="/" component={HomePage}/>
                <Route onEnter={checkAuth}>
                    <Route path="/login" component={LoginPage}/>
                    <Route path="/register" component={RegisterPage}/>
                    <Route path="/dashboard" component={Dashboard}/>
                </Route>
                <Route path="*" component={NotFound}/>
            </Route>
        </Router>
    </Provider>,
    document.getElementById('app')
);
