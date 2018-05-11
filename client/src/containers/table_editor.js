import React, { Component } from 'react'
import Table from '../components/table'

export default class TableEditor extends Component {
  render () {
    const tableId = this.props.params.tableId
    return (
      <div>
        <h1 className='page-title'>TableBase</h1>
        <Table tableId={tableId} />
      </div>
    )
  }
}
