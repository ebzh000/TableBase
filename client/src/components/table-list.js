import React, { Component } from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { bindActionCreators } from 'redux'
import { loadTableHtml, loadTable } from '../actions/table'
import { loadCategories, loadCategoriesNoRoot, loadRootCategories } from '../actions/category'
import { browserHistory } from 'react-router'

class TableList extends Component {
  constructor (props) {
    super(props)

    this.handleClick = this.handleClick.bind(this)
    this.renderTable = this.renderTable.bind(this)
  }

  handleClick (e) {
    e.preventDefault()
    const tableId = e.target.id
    this.props.loadTable(tableId)
    this.props.loadTableHtml(tableId)
    this.props.loadCategories(tableId)
    this.props.loadCategoriesNoRoot(tableId)
    this.props.loadRootCategories(tableId)

    setTimeout(() => {
      browserHistory.push('/table/' + tableId)
    }, 300)
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
      <div className='pad-top-1'>
        <table className='search-table table table-hover'>
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
      </div>
    )
  }
}

TableList.propTypes = {
  tableList: PropTypes.array,
  loadTableHtml: PropTypes.func,
  loadTable: PropTypes.func,
  loadCategories: PropTypes.func,
  loadCategoriesNoRoot: PropTypes.func,
  loadRootCategories: PropTypes.func
}

function mapStateToProps ({ tableList }) {
  return { tableList }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ loadTable, loadTableHtml, loadCategories, loadCategoriesNoRoot, loadRootCategories }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(TableList)
