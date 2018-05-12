import React, { Component } from 'react'
import SearchBar from '../components/search-bar'
import TableList from '../components/table-list'
import CreateTablePopup from '../components/forms/create-table-popup'

class Main extends Component {
  constructor () {
    super()

    this.state = {
      showPopup: false
    }

    this.togglePopup = this.togglePopup.bind(this)
  }

  togglePopup () {
    this.setState({
      showPopup: !this.state.showPopup
    })
  }

  render () {
    return (
      <div>
        <h1 className='page-title'>TableBase</h1>
        <SearchBar />
        <div className='pad-top-2'>
          <button onClick={this.togglePopup}>Create Table</button>
        </div>
        <TableList />
        {this.state.showPopup ? 
          <CreateTablePopup closePopup={this.togglePopup} />
          : null
        }
      </div>
    )
  }
}

export default Main
