import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, loadCategoriesNoRoot, updateCategory } from '../../actions/category'

class UpdateCategory extends Component {
  constructor (props) {
    super(props)

    this.state = {
      categoryName: '',
      parentId: this.props.categories[0].categoryId,
      categoryId: this.props.categoriesNoRoot[0].categoryId,
      linkChildren: false
    }

    this.renderCategoryOptions = this.renderCategoryOptions.bind(this)
    this.onCategoryNameChange = this.onCategoryNameChange.bind(this)
    this.onParentCategoryChange = this.onParentCategoryChange.bind(this)
    this.onCategoryChange = this.onCategoryChange.bind(this)
    this.toggleLinkChildren = this.toggleLinkChildren.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onCategoryNameChange (event) {
    this.setState({ categoryName: event.target.value })
  }

  onFormSubmit (event) {
    event.preventDefault()
    const categoryId = parseInt(this.state.categoryId, 10)
    const parentId = parseInt(this.state.parentId, 10)
    
    this.props.updateCategory(this.props.table.tableId, categoryId, this.state.categoryName, parentId, this.state.linkChildren)
    this.setState({ categoryName: '', categoryId: this.props.categoriesNoRoot[0].categoryId, parentId: this.props.categories[0].categoryId, parentName: 'Select', linkChildren: false })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.closeUpdateCategoryPopup()
    }, 100)
  }

  onParentCategoryChange (event) {
    this.setState({
      parentId: event.target.value
    })
  }

  onCategoryChange (event) {
    const category = this.props.categories.find((category) => category.categoryId == event.target.value)
    this.setState({
      categoryId: category.categoryId,
      parentId: category.parentId,
      categoryName: category.attributeName
    })
  }

  toggleLinkChildren (event) {
    this.setState({ linkChildren: !this.state.linkChildren })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({ categoryName: '', catergoryId: this.props.categoriesNoRoot[0].categoryId, parentId: this.props.categories[0].categoryId, parentName: 'Select', linkChildren: false })
    this.props.closeUpdateCategoryPopup()
  }

  componentDidMount () {
    setTimeout(
      this.setState({
        categoryId: this.props.categoriesNoRoot[0].categoryId,
        parentId: this.props.categoriesNoRoot[0].parentId,
        categoryName: this.props.categoriesNoRoot[0].attributeName
      }), 100)
  }

  renderCategoryOptions (category) {
    // console.log(category)
    return <option key={category.categoryId} value={category.categoryId}>{category.categoryId}| {category.attributeName}</option>
  }

  render () {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Update Category</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Select Category: </label></td>
                    <td>
                      <select value={this.state.categoryId} onChange={this.onCategoryChange} required>
                        {this.props.categoriesNoRoot.map(this.renderCategoryOptions)}
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <td><label>New Category Name: </label></td>
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
                      <select value={this.state.parentId} onChange={this.onParentCategoryChange} required>
                        {this.props.categories.map(this.renderCategoryOptions)}
                      </select>
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

UpdateCategory.propTypes = {
  updateCategory: PropTypes.func,
  loadTableHtml: PropTypes.func,
  loadCategories: PropTypes.func,
  loadCategoriesNoRoot: PropTypes.func,
  categories: PropTypes.array,
  categoriesNoRoot: PropTypes.array,
  table: PropTypes.object
}

function mapStateToProps ({ table, categories, categoriesNoRoot }) {
  return { table, categories, categoriesNoRoot }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ updateCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(UpdateCategory)
