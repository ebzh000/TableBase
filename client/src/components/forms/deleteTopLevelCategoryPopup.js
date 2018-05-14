import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, loadCategoriesNoRoot, loadRootCategories, deleteTopLevelCategory } from '../../actions/category'

class DeleteCategory extends Component {
  constructor (props) {
    super(props)

    this.state = { categoryId: this.props.rootCategories[0], dataOperationType: 0 }

    this.renderCategoryOptions = this.renderCategoryOptions.bind(this)
    this.renderOperationTypeOptions = this.renderOperationTypeOptions.bind(this)
    this.onCategoryChange = this.onCategoryChange.bind(this)
    this.onDataOperationTypeChange = this.onDataOperationTypeChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onFormSubmit (event) {
    event.preventDefault()
    console.log(this.state.categoryId)
    this.props.deleteTopLevelCategory(this.props.table.tableId, this.state.categoryId, this.state.dataOperationType)
    this.setState({ categoryId: this.props.rootCategories[0] })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.loadRootCategories(this.props.table.tableId)
      this.props.closeDeleteTopLevelCategoryPopup()
    }, 100)
  }

  onCategoryChange (event) {
    this.setState({
      categoryId: event.target.value
    })
  }

  onDataOperationTypeChange (event) {
    this.setState({
      dataOperationType: event.target.value
    })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({ carentCategoryId: this.props.rootCategories[0] })
    this.props.closeDeleteTopLevelCategoryPopup()
  }

  renderCategoryOptions (category) {
    return <option key={category.categoryId} value={category.categoryId}>{category.attributeName}</option>
  }

  renderOperationTypeOptions (dataOperationType) {
    return <option key={dataOperationType.id} value={dataOperationType.id}>{dataOperationType.name}</option>
  }

  render () {
    const dataOperationTypes = [
      {id: 0, name: 'MAX'},
      {id: 1, name: 'MIN'},
      {id: 2, name: 'MEAN'},
      {id: 5, name: 'CONCATENATE'}
    ]
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Delete Top Level Category</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Select RootCategory: </label></td>
                    <td>
                      <select className='form-control' value={this.state.categoryId} onChange={this.onCategoryChange} required>
                        {this.props.rootCategories.map(this.renderCategoryOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label>Select Data Operation Type: </label></td>
                    <td>
                      <select className='form-control' value={this.state.dataOperationType} onChange={this.onDataOperationTypeChange} required>
                        {dataOperationTypes.map(this.renderOperationTypeOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr><td><label /></td></tr>
                  <tr>
                    <td></td>
                    <td><button type='submit'>Delete</button> <button onClick={this.onClose}>Cancel</button></td>
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

DeleteCategory.propTypes = {
  deleteTopLevelCategory: PropTypes.func,
  loadTableHtml: PropTypes.func,
  loadCategories: PropTypes.func,
  loadCategoriesNoRoot: PropTypes.func,
  loadRootCategories: PropTypes.func,
  rootCategories: PropTypes.array,
  table: PropTypes.object
}

function mapStateToProps ({ table, rootCategories }) {
  return { table, rootCategories }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ deleteTopLevelCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot, loadRootCategories }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(DeleteCategory)
