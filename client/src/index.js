import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware } from 'redux'
import ReduxPromise from 'redux-promise'
import { Router, Route, browserHistory } from 'react-router'

import Main from './containers/main'
import TableEditor from './containers/table-editor'
import reducers from './reducers'

const createStoreWithMiddleware = applyMiddleware(ReduxPromise)(createStore)

ReactDOM.render(
  <Provider store={createStoreWithMiddleware(reducers)}>
    <Router history={browserHistory}>
      <Route path={'/'} component={Main} />
      <Route path={'table/:tableId'} component={TableEditor} />
    </Router>
  </Provider>,
  document.querySelector('.container')
)
