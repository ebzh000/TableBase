import React, { Component } from 'react'
import CreateTopLevelCategory from './forms/createTopLevelCategoryPopup'
import CreateCategory from './forms/createCategoryPopup'
import UpdateCategory from './forms/updateCategoryPopup'
import DuplicateCategory from './forms/duplicateCategoryPopup'
import DeleteCategory from './forms/deleteCategoryPopup'
import DeleteTopLevelCategory from './forms/deleteTopLevelCategoryPopup'
import SplitCategory from './forms/splitCategoryPopup'
import CombineCategory from './forms/combineCategoryPopup'
import UpdateEntry from './forms/updateEntryPopup'

class Buttons extends Component {
  constructor () {
    super()

    this.state = {
      showCreateTopLevelCategoryPopup: false,
      showCreateCategoryPopup: false,
      showUpdateCategoryPopup: false,
      showDuplicateCategoryPopup: false,
      showSplitCategoryPopup: false,
      showCombineCategoryPopup: false,
      showDeleteTopLevelCategoryPopup: false,
      showDeleteCategoryPopup: false,
      showUpdateEntryPopup: false
    }

    this.toggleCreateTopLevelCategoryPopup = this.toggleCreateTopLevelCategoryPopup.bind(this)
    this.toggleCreateCategoryPopup = this.toggleCreateCategoryPopup.bind(this)
    this.toggleUpdateCategoryPopup = this.toggleUpdateCategoryPopup.bind(this)
    this.toggleDuplicateCategoryPopup = this.toggleDuplicateCategoryPopup.bind(this)
    this.toggleSplitCategoryPopup = this.toggleSplitCategoryPopup.bind(this)
    this.toggleCombineCategoryPopup = this.toggleCombineCategoryPopup.bind(this)
    this.toggleDeleteTopLevelCategoryPopup = this.toggleDeleteTopLevelCategoryPopup.bind(this)
    this.toggleDeleteCategoryPopup = this.toggleDeleteCategoryPopup.bind(this)
    this.toggleUpdateEntryPopup = this.toggleUpdateEntryPopup.bind(this)
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

  toggleCombineCategoryPopup () {
    this.setState({
      showCombineCategoryPopup: !this.state.showCombineCategoryPopup
    })
  }

  toggleUpdateEntryPopup () {
    this.setState({
      showUpdateEntryPopup: !this.state.showUpdateEntryPopup
    })
  }

  render () {
    return (
      <div className='edit-buttons pad-top-5 pad-left'>
        <table>
          <tbody>
            <tr>
              <td>
                <button onClick={this.toggleCreateTopLevelCategoryPopup}>Create Top Level Category</button>
                {this.state.showCreateTopLevelCategoryPopup ?
                  <CreateTopLevelCategory closeCreateTopLevelCategoryPopup={this.toggleCreateTopLevelCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleCreateCategoryPopup}>Create Category</button>
                {this.state.showCreateCategoryPopup ?
                  <CreateCategory closeCreateCategoryPopup={this.toggleCreateCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleUpdateCategoryPopup}>Update Category</button>
                {this.state.showUpdateCategoryPopup ?
                  <UpdateCategory closeUpdateCategoryPopup={this.toggleUpdateCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleDuplicateCategoryPopup}>Duplicate Category</button>
                {this.state.showDuplicateCategoryPopup ?
                  <DuplicateCategory closeDuplicateCategoryPopup={this.toggleDuplicateCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleUpdateEntryPopup}>Update Entry</button>
                {this.state.showUpdateEntryPopup ?
                  <UpdateEntry closeUpdateEntryPopup={this.toggleUpdateEntryPopup} />
                  : null
                }
              </td>
            </tr>
            <tr>
              <td>
                <button onClick={this.toggleDeleteTopLevelCategoryPopup}>Delete Top Level Category</button>
                {this.state.showDeleteTopLevelCategoryPopup ?
                  <DeleteTopLevelCategory closeDeleteTopLevelCategoryPopup={this.toggleDeleteTopLevelCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleDeleteCategoryPopup}>Delete Category</button>
                {this.state.showDeleteCategoryPopup ?
                  <DeleteCategory closeDeleteCategoryPopup={this.toggleDeleteCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleSplitCategoryPopup}>Split Category</button>
                {this.state.showSplitCategoryPopup ?
                  <SplitCategory closeSplitCategoryPopup={this.toggleSplitCategoryPopup} />
                  : null
                }
              </td>
              <td>
                <button onClick={this.toggleCombineCategoryPopup}>Combine Category</button>
                {this.state.showCombineCategoryPopup ?
                  <CombineCategory closeCombineCategoryPopup={this.toggleCombineCategoryPopup} />
                  : null
                }
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    )
  }
}

export default Buttons
