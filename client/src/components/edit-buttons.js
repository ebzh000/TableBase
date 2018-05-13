import React, { Component } from 'react'
import CreateTopLevelCategory from './forms/createTopLevelCategoryPopup'
import CreateCategory from './forms/createCategoryPopup'
import UpdateCategory from './forms/updateCategoryPopup'
import DuplicateCategory from './forms/duplicateCategoryPopup'
import DeleteCategory from './forms/deleteCategoryPopup'
import DeleteTopLevelCategory from './forms/deleteTopLevelCategoryPopup'
import SplitCategory from './forms/splitCategoryPopup'

class Buttons extends Component {
  constructor () {
    super()

    this.state = {
      showCreateTopLevelCategoryPopup: false,
      showCreateCategoryPopup: false,
      showUpdateCategoryPopup: false,
      showDuplicateCategoryPopup: false,
      showSplitCategoryPopup: false,
      showDeleteTopLevelCategoryPopup: false,
      showDeleteCategoryPopup: false
    }

    this.toggleCreateTopLevelCategoryPopup = this.toggleCreateTopLevelCategoryPopup.bind(this)
    this.toggleCreateCategoryPopup = this.toggleCreateCategoryPopup.bind(this)
    this.toggleUpdateCategoryPopup = this.toggleUpdateCategoryPopup.bind(this)
    this.toggleDuplicateCategoryPopup = this.toggleDuplicateCategoryPopup.bind(this)
    this.toggleSplitCategoryPopup = this.toggleSplitCategoryPopup.bind(this)
    this.toggleDeleteTopLevelCategoryPopup = this.toggleDeleteTopLevelCategoryPopup.bind(this)
    this.toggleDeleteCategoryPopup = this.toggleDeleteCategoryPopup.bind(this)
  }

  toggleCreateTopLevelCategoryPopup () {
    this.setState({
      showCreateTopLevelCategoryPopup: !this.state.showCreateTopLevelCategoryPopup
    })
  }

  toggleDeleteTopLevelCategoryPopup () {
    this.setState({
      showDeleteTopLevelCategoryPopup: !this.state.showDeleteTopLevelCategoryPopup
    })
  }

  toggleCreateCategoryPopup () {
    this.setState({
      showCreateCategoryPopup: !this.state.showCreateCategoryPopup
    })
  }

  toggleUpdateCategoryPopup () {
    this.setState({
      showUpdateCategoryPopup: !this.state.showUpdateCategoryPopup
    })
  }

  toggleDeleteCategoryPopup () {
    this.setState({
      showDeleteCategoryPopup: !this.state.showDeleteCategoryPopup
    })
  }

  toggleDuplicateCategoryPopup () {
    this.setState({
      showDuplicateCategoryPopup: !this.state.showDuplicateCategoryPopup
    })
  }

  toggleSplitCategoryPopup () {
    this.setState({
      showSplitCategoryPopup: !this.state.showSplitCategoryPopup
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

        <button onClick={this.toggleUpdateCategoryPopup}>Update Category</button>
        {this.state.showUpdateCategoryPopup ?
          <UpdateCategory closeUpdateCategoryPopup={this.toggleUpdateCategoryPopup} />
          : null
        }

        <button onClick={this.toggleDuplicateCategoryPopup}>Duplicate Category</button>
        {this.state.showDuplicateCategoryPopup ?
          <DuplicateCategory closeDuplicateCategoryPopup={this.toggleDuplicateCategoryPopup} />
          : null
        }

        <button onClick={this.toggleSplitCategoryPopup}>Split Category</button>
        {this.state.showSplitCategoryPopup ?
          <SplitCategory closeSplitCategoryPopup={this.toggleSplitCategoryPopup} />
          : null
        }

        <button>Combine Category</button>

        <button onClick={this.toggleDeleteTopLevelCategoryPopup}>Delete Top Level Category</button>
        {this.state.showDeleteTopLevelCategoryPopup ?
          <DeleteTopLevelCategory closeDeleteTopLevelCategoryPopup={this.toggleDeleteTopLevelCategoryPopup} />
          : null
        }

        <button onClick={this.toggleDeleteCategoryPopup}>Delete Category</button>
        {this.state.showDeleteCategoryPopup ?
          <DeleteCategory closeDeleteCategoryPopup={this.toggleDeleteCategoryPopup} />
          : null
        }
      </div>
    )
  }
}

export default Buttons
