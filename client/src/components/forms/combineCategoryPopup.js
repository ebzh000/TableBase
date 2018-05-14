import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, loadCategoriesNoRoot, combineCategory } from '../../actions/category'

class CombineCategory extends Component {
  constructor (props) {
    super(props)

    this.state = {
      categoryId1: this.props.categoriesNoRoot[0],
      categoryId2: this.props.categoriesNoRoot[1],
      categoryName: '',
      dataOperationType: 0,
      threshold: 0
    }

    this.renderCategoryOptions = this.renderCategoryOptions.bind(this)
    this.renderOperationTypeOptions = this.renderOperationTypeOptions.bind(this)
    this.onCategory1Change = this.onCategory1Change.bind(this)
    this.onCategory2Change = this.onCategory2Change.bind(this)
    this.onNameChange = this.onNameChange.bind(this)
    this.onDataOperationTypeChange = this.onDataOperationTypeChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onFormSubmit (event) {
    event.preventDefault()

    this.props.combineCategory(this.props.table.tableId, this.state.categoryId1, this.state.categoryId2, this.state.categoryName, this.state.dataOperationType, this.state.threshold)
    this.setState({ categoryId1: this.props.categoriesNoRoot[0] })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.closeCombineCategoryPopup()
    }, 100)
  }

  onNameChange (event) {
    this.setState({ categoryName: event.target.value })
  }

  onCategory1Change (event) {
    this.setState({
      categoryId1: parseInt(event.target.value, 10)
    })
  }

  onCategory2Change (event) {
    this.setState({
      categoryId2: parseInt(event.target.value, 10)
    })
  }

  onDataOperationTypeChange (event) {
    this.setState({
      dataOperationType: parseInt(event.target.value, 10)
    })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({ categoryId1: this.props.categoriesNoRoot[0] })
    this.props.closeCombineCategoryPopup()
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
      {id: 3, name: 'SUM'},
      {id: 4, name: 'DIFFERENCE'},
      {id: 5, name: 'CONCATENATE'},
      {id: 6, name: 'LEFT'},
      {id: 7, name: 'RIGHT'}
    ]
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Combine Category</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Select Category 1: </label></td>
                    <td>
                      <select className='form-control' value={this.state.categoryId1} onChange={this.onCategory1Change} required>
                        {this.props.categoriesNoRoot.map(this.renderCategoryOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label>Select Category 2: </label></td>
                    <td>
                      <select className='form-control' value={this.state.categoryId2} onChange={this.onCategory2Change} required>
                        {this.props.categoriesNoRoot.map(this.renderCategoryOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label>Category Name: </label></td>
                    <td><input
                      placeholder='Enter New Category Name'
                      className='form-control'
                      value={this.state.categoryName}
                      onChange={this.onNameChange}
                      required
                    /></td>
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
                    <td><button type='submit'>Combine</button> <button onClick={this.onClose}>Cancel</button></td>
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

CombineCategory.propTypes = {
  combineCategory: PropTypes.func,
  loadTableHtml: PropTypes.func,
  loadCategories: PropTypes.func,
  loadCategoriesNoRoot: PropTypes.func,
  categoriesNoRoot: PropTypes.array,
  table: PropTypes.object
}

function mapStateToProps ({ table, categoriesNoRoot }) {
  return { table, categoriesNoRoot }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ combineCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(CombineCategory)
