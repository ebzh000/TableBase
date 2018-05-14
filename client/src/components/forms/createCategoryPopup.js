import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, createCategory, loadCategoriesNoRoot } from '../../actions/category'

class CreateCategory extends Component {
  constructor (props) {
    super(props)

    this.state = { categoryName: '', parentCategoryId: this.props.categories[0].categoryId, linkChildren: false, dataType: 1 }

    this.renderCategoryOptions = this.renderCategoryOptions.bind(this)
    this.renderDataTypeOptions = this.renderDataTypeOptions.bind(this)
    this.onCategoryNameChange = this.onCategoryNameChange.bind(this)
    this.onParentCategoryChange = this.onParentCategoryChange.bind(this)
    this.onDataTypeChange = this.onDataTypeChange.bind(this)
    this.toggleLinkChildren = this.toggleLinkChildren.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onCategoryNameChange (event) {
    this.setState({ categoryName: event.target.value })
  }

  onFormSubmit (event) {
    event.preventDefault()
    this.props.createCategory(this.props.table.tableId, this.state.categoryName, this.state.parentCategoryId, this.state.linkChildren, this.state.dataType)
    this.setState({ categoryName: '', parentCategoryId: this.props.categories[0].categoryId, parentName: 'Select', linkChildren: false, dataType: 1 })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.closeCreateCategoryPopup()
    }, 100)
  }

  onParentCategoryChange (event) {
    this.setState({
      parentCategoryId: event.target.value
    })
  }

  onDataTypeChange (event) {
    this.setState({
      dataType: parseInt(event.target.value, 10)
    })
  }

  toggleLinkChildren (event) {
    this.setState({ linkChildren: !this.state.linkChildren })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({ categoryName: '', parentCategoryId: this.props.categories[0].categoryId, parentName: 'Select', linkChildren: false, dataType: 1 })
    this.props.closeCreateCategoryPopup()
  }

  renderCategoryOptions (category) {
    return <option key={category.categoryId} value={category.categoryId}>{category.attributeName}</option>
  }

  renderDataTypeOptions (dataType) {
    return <option key={dataType.id} value={dataType.id}>{dataType.name}</option>
  }

  render () {
    const types = [
      {id: 1, name: 'INTEGER'},
      {id: 2, name: 'TEXT'},
      {id: 4, name: 'PERCENT'},
      {id: 6, name: 'DECIMAL'}
    ]
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Create Category</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Category Name: </label></td>
                    <td><input
                      placeholder='Enter New Category Name'
                      className='form-control'
                      value={this.state.categoryName}
                      onChange={this.onCategoryNameChange}
                      required
                    /></td>
                  </tr>
                  <tr>
                    <td><label>Select Parent: </label></td>
                    <td>
                      <select className='form-control' value={this.state.parentCategoryId} onChange={this.onParentCategoryChange} required>
                        {this.props.categories.map(this.renderCategoryOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label>Select Data Type: </label></td>
                    <td>
                      <select className='form-control' value={this.state.dataType} onChange={this.onDataTypeChange} required>
                        {types.map(this.renderDataTypeOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label>Link Children: </label></td>
                    <td>
                      <input type='checkbox' name='linkChildren' checked={this.state.linkChildren} onChange={this.toggleLinkChildren} />
                    </td>
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

CreateCategory.propTypes = {
  createCategory: PropTypes.func,
  loadTableHtml: PropTypes.func,
  loadCategories: PropTypes.func,
  loadCategoriesNoRoot: PropTypes.func,
  categories: PropTypes.array,
  table: PropTypes.object
}

function mapStateToProps ({ table, categories }) {
  return { table, categories }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ createCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateCategory)
