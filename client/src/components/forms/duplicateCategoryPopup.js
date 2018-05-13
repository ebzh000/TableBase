import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { loadCategories, loadCategoriesNoRoot, duplicateCategory } from '../../actions/category'

class DuplicateCategory extends Component {
  constructor (props) {
    super(props)

    this.state = { categoryId: this.props.categoriesNoRoot[0] }

    this.renderCategoryOptions = this.renderCategoryOptions.bind(this)
    this.onCategoryChange = this.onCategoryChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onFormSubmit (event) {
    event.preventDefault()

    this.props.duplicateCategory(this.props.table.tableId, this.state.categoryId)
    this.setState({ categoryId: this.props.categoriesNoRoot[0] })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.loadCategories(this.props.table.tableId)
      this.props.loadCategoriesNoRoot(this.props.table.tableId)
      this.props.closeDuplicateCategoryPopup()
    }, 100)
  }

  onCategoryChange (event) {
    this.setState({
      categoryId: event.target.value
    })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({ carentCategoryId: this.props.categoriesNoRoot[0] })
    this.props.closeDuplicateCategoryPopup()
  }

  renderCategoryOptions (category) {
    return <option key={category.categoryId} value={category.categoryId}>{category.attributeName}</option>
  }

  render () {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Duplicate Category</h1>
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
                  <tr><td><label /></td></tr>
                  <tr>
                    <td></td>
                    <td><button type='submit'>Duplicate</button> <button onClick={this.onClose}>Cancel</button></td>
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

DuplicateCategory.propTypes = {
  duplicateCategory: PropTypes.func,
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
  return bindActionCreators({ duplicateCategory, loadTableHtml, loadCategories, loadCategoriesNoRoot }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(DuplicateCategory)
