import React, { Component } from 'react'
import SearchBar from '../containers/search_bar.js'
import TableList from '../containers/table_list.js'

export default class App extends Component {
  render () {
    return (
      <div>
        <h1>TableBase</h1>
        <SearchBar />
        <TableList />
      </div>
    )
  }
}
