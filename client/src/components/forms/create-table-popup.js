import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { createTable, loadTableHtml } from '../../actions/table'
import { loadCategories, loadRootCategories, loadCategoriesNoRoot } from '../../actions/category'
import { browserHistory } from 'react-router'

class CreateTablePopup extends Component {
  constructor (props) {
    super(props)

    this.state = { tableName: '', tags: '' }

    this.onTableNameChange = this.onTableNameChange.bind(this)
    this.onTableTagChange = this.onTableTagChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onTableNameChange (event) {
    this.setState({ tableName: event.target.value })
  }

  onTableTagChange (event) {
    this.setState({ tags: event.target.value })
  }

  onFormSubmit (event) {
    event.preventDefault()

    this.props.createTable(this.state.tableName, this.state.tags, 1)
    this.setState({ tableName: '', tags: '' })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.loadRootCategories(this.props.table.tableId)
      browserHistory.push('/table/' + this.props.table.tableId)
    }, 500)
  }

  onClose (event) {
    event.preventDefault()

    this.setState({tableName: '', tags: ''})
    this.props.closePopup()
  }

  render () {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Create Table</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Table Name: </label></td>
                    <td><input
                      placeholder='Enter a Table Name'
                      className='form-control'
                      value={this.state.tableName}
                      onChange={this.onTableNameChange}
                      required
                    /></td>
                  </tr>
                  <tr>
                    <td><label>Table Tags: </label></td>
                    <td><input
                      placeholder="Enter Tags (Separated by ', ')"
                      className='form-control'
                      value={this.state.tags}
                      onChange={this.onTableTagChange}
                      required
                    /></td>
                  </tr>
                  <tr><td><label /></td></tr>
                  <tr>
                    <td></td>
                    <td><button type='submit'>Create</button> <button onClick={this.onClose}>Cancel</button></td>
                  </tr>
                </tbody>
              </table>
            </form>
          </div>
        </div>
      </div>
    )
  }
}

CreateTablePopup.propTypes = {
  createTable: PropTypes.func,
  loadTableHtml: PropTypes.func,
  loadCategories: PropTypes.func,
  loadRootCategories: PropTypes.func,
  loadCategoriesNoRoot: PropTypes.func,
  table: PropTypes.object
}

function mapStateToProps ({ table }) {
  return { table }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ createTable, loadTableHtml, loadCategories, loadRootCategories, loadCategoriesNoRoot }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateTablePopup)
