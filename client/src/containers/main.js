import React, { Component } from 'react'
import SearchBar from '../components/search_bar'
import TableList from '../components/table_list'

export default class Main extends Component {
  render () {
    return (
      <div>
        <h1 className='page-title'>TableBase</h1>
        <SearchBar />
        <TableList />
      </div>
    )
  }
}
