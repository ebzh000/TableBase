import React, { Component } from 'react'
import SearchBar from '../components/search-bar'
import TableList from '../components/table-list'
import { Popup } from 'reactjs-popup'

class Main extends Component {
  render () {
    return (
      <div>
        <h1 className='page-title'>TableBase</h1>
        <SearchBar />
        // Need to add a pop up menu here
        <TableList />
      </div>
    )
  }
}

export default Main
