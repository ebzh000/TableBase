import React, { Component } from 'react'
import Table from '../components/table'
import Buttons from '../components/edit-buttons'

class TableEditor extends Component {
  render () {
    console.log(this.props)
    const tableId = this.props.params.tableId
    return (
      <div>
        <h1 className='page-title'>TableBase</h1>
        <Table tableId={tableId} />
        <Buttons tableId={tableId} />
      </div>
    )
  }
}

export default TableEditor
