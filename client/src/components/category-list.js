import React, { Component } from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'

class CategoryList extends Component {
  constructor (props) {
    super(props)

    this.handleClick = this.handleClick.bind(this)
    this.renderOptions = this.renderOptions.bind(this)
  }

  renderOptions (category) {
    return (
      <option value={category.categoryId}>{category.attributeName}}</option>
    )
  }

  render () {
    return (
      <div name='categoryList'>
        <select name='categories'>
          {this.props.categories.map(this.renderOptions)}
        </select>
      </div>
    )
  }
}

CategoryList.propTypes = {
  categories: PropTypes.array
}

function mapStateToProps ({ categories }) {
  return { categories }
}

export default connect(mapStateToProps, null)(CategoryList)
