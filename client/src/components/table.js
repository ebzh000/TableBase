import React, { Component } from 'react'

export default class Table extends Component {
  render () {
    return (
      <div>
        <h1>TableBase</h1>
        <h2>THIS IS WHERE WE TABLE {this.props.params.tableId}</h2>
      </div>
    )
  }
}
