import React, { Component } from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { browserHistory } from 'react-router'
import { bindActionCreators } from 'redux'
import { loadTableHtml, loadTable } from '../actions'

class TableList extends Component {
  constructor (props) {
    super(props)

    this.handleClick = this.handleClick.bind(this)
    this.renderTable = this.renderTable.bind(this)
  }

  handleClick (e) {
    e.preventDefault()
    this.props.loadTable(e.target.id)
    this.props.loadTableHtml(e.target.id)
    browserHistory.push('/table/' + e.target.id)
  }

  renderTable (tableData) {
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
          <button id={tableData.tableId} onClick={this.handleClick}>Edit</button>
        </td>
      </tr>
    )
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
        <tbody>{this.props.tableList.map(this.renderTable)}</tbody>
      </table>
    )
  }
}

TableList.propTypes = {
  tableList: PropTypes.array,
  loadTableHtml: PropTypes.func,
  loadTable: PropTypes.func
}

function mapStateToProps ({ tableList }) {
  return { tableList }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ loadTable, loadTableHtml }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(TableList)
