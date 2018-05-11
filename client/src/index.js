import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import ReduxPromise from 'redux-promise'
import { Router, Route, browserHistory } from 'react-router'

import App from './containers/app'
import TableEditor from './containers/table_editor'
import reducers from './reducers'

const createStoreWithMiddleware = applyMiddleware(ReduxPromise)(createStore)

ReactDOM.render(
  <Provider store={createStoreWithMiddleware(reducers)}>
    <Router history={browserHistory}>
      <Route path={'/'} component={App} />
      <Route path={'/table/:tableId'} component={TableEditor} />
    </Router>
  </Provider>,
  document.querySelector('.container')
)
