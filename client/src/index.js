import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import ReduxPromise from 'redux-promise'
import reducers from './reducers'

import App from "./containers/App";
import LoginPage from "./containers/LoginPage";
import RegisterPage from "./containers/RegisterPage";
import TablePage from "./containers/TablePage";
import Dashboard from './containers/DashboardPage';
import NotFoundPage from "./containers/NotFoundPage";

const createStoreWithMiddleware = applyMiddleware(ReduxPromise)(createStore);

ReactDOM.render(
  <Provider store={createStoreWithMiddleware(reducers)}>
      <Router history={browserHistory}>
          <Route>
              <Route path="login" component={LoginPage}/>
              <Route path="/" component={App}>
                  <IndexRoute component={LoginPage}/>
                  <Route path="dashboard" component={Dashboard}/>
                  <Route path="register" component={RegisterPage}/>
                  <Route path="table" component={TablePage}/>
                  <Route path="*" component={NotFoundPage}/>
              </Route>
          </Route>
      </Router>
  </Provider>,
  document.querySelector('.container')
)
