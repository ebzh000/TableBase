import React, { Component } from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'

class Buttons extends Component {
  render () {
    console.log(this.props.categories)
    return (
      <div className='edit-buttons'>
        <button>Create Top Level Category</button>
        <button>Create Category</button>
        <button>Update Category</button>
        <button>Duplicate Category</button>
        <button>Split Category</button>
        <button>Combine Category</button>
        <button>Delete Top Level Category</button>
        <button>Delete Category</button>
      </div>
    )
  }
}

Buttons.propTypes = {
  table: PropTypes.object,
  categories: PropTypes.array
}

function mapStateToProps ({ categories, table }) {
  return { categories, table }
}

export default connect(mapStateToProps, null)(Buttons)
