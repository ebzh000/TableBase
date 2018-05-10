import React, { Component } from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { Link, browserhistory } from 'react-router'

class TableList extends Component {
  constructor (props) {
    super(props)

    this.state = { tableId: '' }

    this.handleClick = this.handleClick.bind(this)
  }

  handleClick (event) {
    console.log('this is:', event)
  }

  renderTable (tableList) {
    return tableList.map(tableData => {
      return (
        <tr key={tableData.tableId}>
          <td>
            {tableData.tableId}
          </td>
          <td>
            {tableData.tableName}
          </td>
          <td>
            {tableData.tags}
          </td>
          <td>
            <Link to={'/table/' + tableData.tableId}>Edit</Link>
          </td>
        </tr>
      )
    })
  }

  render () {
    return (
      <table className='table table-hover'>
        <thead>
          <tr>
            <th>ID</th>
            <th>Table Name</th>
            <th>Tags</th>
            <th></th>
          </tr>
        </thead>
        <tbody>{this.props.table.map(this.renderTable)}</tbody>
      </table>
    )
  }
}

TableList.propTypes = {
  table: PropTypes.arrayOf(PropTypes.array)
}

function mapStateToProps ({ table }) {
  return { table }
}

export default connect(mapStateToProps)(TableList)
