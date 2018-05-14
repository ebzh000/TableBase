import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, loadCategoriesNoRoot, loadRootCategories, createTopLevelCategory } from '../../actions/category'

class CreateTopLevelCategory extends Component {
  constructor (props) {
    super(props)

    this.state = { categoryName: '', topCategoryName: '' }

    this.onCategoryNameChange = this.onCategoryNameChange.bind(this)
    this.onTopCategoryNameChange = this.onTopCategoryNameChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onCategoryNameChange (event) {
    this.setState({ categoryName: event.target.value })
  }

  onTopCategoryNameChange (event) {
    this.setState({ topCategoryName: event.target.value })
  }

  onFormSubmit (event) {
    event.preventDefault()

    this.props.createTopLevelCategory(this.props.table.tableId, this.state.topCategoryName, this.state.categoryName)
    this.setState({ categoryName: '' })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadRootCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.closeCreateTopLevelCategoryPopup()
    }, 100)
  }

  onClose (event) {
    event.preventDefault()

    this.setState({categoryName: ''})
    this.props.closeCreateTopLevelCategoryPopup()
  }

  render () {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Create Top Level Category</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Top Level Category Name: </label></td>
                    <td><input
                      placeholder='Enter a Name'
                      className='form-control'
                      value={this.state.topCategoryName}
                      onChange={this.onTopCategoryNameChange}
                      required
                    /></td>
                  </tr>
                  <tr>
                    <td><label>Category Name: </label></td>
                    <td><input
                      placeholder='Enter a Name'
                      className='form-control'
                      value={this.state.categoryName}
                      onChange={this.onCategoryNameChange}
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

CreateTopLevelCategory.propTypes = {
  createTopLevelCategory: PropTypes.func,
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
  return bindActionCreators({ createTopLevelCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot, loadRootCategories }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateTopLevelCategory)
