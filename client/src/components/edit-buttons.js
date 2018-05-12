import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import CreateTopLevelCategory from './forms/createTopLevelCategoryPopup'
import CreateCategory from './forms/createCategoryPopup'

class Buttons extends Component {
  constructor () {
    super()

    this.state = {
      showCreateTopLevelCategoryPopup: false,
      showCreateCategoryPopup: false
    }

    this.toggleCreateTopLevelCategoryPopup = this.toggleCreateTopLevelCategoryPopup.bind(this)
    this.toggleCreateCategoryPopup = this.toggleCreateCategoryPopup.bind(this)
  }

  toggleCreateTopLevelCategoryPopup () {
    this.setState({
      showCreateTopLevelCategoryPopup: !this.state.showCreateTopLevelCategoryPopup
    })
  }

  toggleCreateCategoryPopup () {
    this.setState({
      showCreateCategoryPopup: !this.state.showCreateCategoryPopup
    })
  }

  render () {
    return (
      <div className='edit-buttons'>
        <button onClick={this.toggleCreateTopLevelCategoryPopup}>Create Top Level Category</button>
        {this.state.showCreateTopLevelCategoryPopup ?
          <CreateTopLevelCategory closeCreateTopLevelCategoryPopup={this.toggleCreateTopLevelCategoryPopup} />
          : null
        }

        <button onClick={this.toggleCreateCategoryPopup}>Create Category</button>
        {this.state.showCreateCategoryPopup ?
          <CreateCategory closeCreateCategoryPopup={this.toggleCreateCategoryPopup} />
          : null
        }

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

export default Buttons
