import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, loadCategoriesNoRoot, splitCategory } from '../../actions/category'

class SplitCategory extends Component {
  constructor (props) {
    super(props)

    this.state = {
      categoryId: this.props.categoriesNoRoot[0],
      categoryName: '',
      dataOperationType: 8,
      threshold: 0
    }

    this.renderCategoryOptions = this.renderCategoryOptions.bind(this)
    this.renderOperationTypeOptions = this.renderOperationTypeOptions.bind(this)
    this.onCategoryChange = this.onCategoryChange.bind(this)
    this.onNameChange = this.onNameChange.bind(this)
    this.onDataOperationTypeChange = this.onDataOperationTypeChange.bind(this)
    this.onThresholdChange = this.onThresholdChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onFormSubmit (event) {
    event.preventDefault()

    this.props.splitCategory(this.props.table.tableId, this.state.categoryId, this.state.categoryName, this.state.dataOperationType, this.state.threshold)
    this.setState({ categoryId: this.props.categoriesNoRoot[0] })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.closeSplitCategoryPopup()
    }, 100)
  }

  onNameChange (event) {
    this.setState({ categoryName: event.target.value })
  }

  onCategoryChange (event) {
    this.setState({
      categoryId: parseInt(event.target.value, 10)
    })
  }

  onDataOperationTypeChange (event) {
    this.setState({
      dataOperationType: parseInt(event.target.value, 10)
    })
  }

  onThresholdChange (event) {
    this.setState({
      threshold: event.target.value
    })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({ categoryId: this.props.categoriesNoRoot[0] })
    this.props.closeSplitCategoryPopup()
  }

  renderCategoryOptions (category) {
    return <option key={category.categoryId} value={category.categoryId}>{category.attributeName}</option>
  }

  renderOperationTypeOptions (dataOperationType) {
    return <option key={dataOperationType.id} value={dataOperationType.id}>{dataOperationType.name}</option>
  }

  render () {
    const dataOperationTypes = [
      {id: 8, name: 'NO OPERATION'},
      {id: 9, name: 'COPY'},
      {id: 10, name: 'THRESHOLD'}
    ]
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Split Category</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Select Category: </label></td>
                    <td>
                      <select className='form-control' value={this.state.categoryId} onChange={this.onCategoryChange} required>
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
                  {this.state.dataOperationType === 10 ?
                    <tr>
                      <td><label>Threshold: </label></td>
                      <td><input
                        placeholder='Enter Threshold'
                        className='form-control'
                        value={this.state.threshold}
                        onChange={this.onThresholdChange}
                        required
                      /></td>
                    </tr>
                    : null
                  }
                  <tr><td><label /></td></tr>
                  <tr>
                    <td></td>
                    <td><button type='submit'>Split</button> <button onClick={this.onClose}>Cancel</button></td>
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

SplitCategory.propTypes = {
  splitCategory: PropTypes.func,
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
  return bindActionCreators({ splitCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(SplitCategory)
