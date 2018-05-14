import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { loadTableHtml } from '../../actions/table'
import { updateEntry } from '../../actions/entry'

class UpdateEntry extends Component {
  constructor (props) {
    super(props)

    this.state = {
      label: '',
      entryId: 0
    }

    this.onEntryIdChange = this.onEntryIdChange.bind(this)
    this.onLabelChange = this.onLabelChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.onClose = this.onClose.bind(this)
  }

  onFormSubmit (event) {
    event.preventDefault()
    const entryId = parseInt(this.state.entryId, 10)

    this.props.updateEntry(this.props.table.tableId, entryId, this.state.label)
    this.setState({
      label: '',
      entryId: 0
    })

    setTimeout(() => {
      this.props.loadTableHtml(this.props.table.tableId)
      this.props.closeUpdateEntryPopup()
    }, 100)
  }

  onLabelChange (event) {
    this.setState({
      label: event.target.value
    })
  }

  onEntryIdChange (event) {
    this.setState({
      entryId: event.target.value
    })
  }

  onClose (event) {
    event.preventDefault()

    this.setState({
      label: '',
      entryId: 0
    })
    this.props.closeUpdateEntryPopup()
  }

  render () {
    return (
      <div className='popup'>
        <div className='popup_inner'>
          <h1>Update Entry</h1>
          <div className='popup-form-div'>
            <form className='popup-form' onSubmit={this.onFormSubmit}>
              <table>
                <tbody>
                  <tr>
                    <td><label>Enter Entry Id: </label></td>
                    <td><input
                      placeholder='Enter Entry Id'
                      className='form-control'
                      value={this.state.entryId}
                      onChange={this.onEntryIdChange}
                      required
                    /></td>
                  </tr>
                  <tr>
                    <td><label>Enter Label Value: </label></td>
                    <td><input
                      placeholder='Enter value'
                      className='form-control'
                      value={this.state.label}
                      onChange={this.onLabelChange}
                      required
                    /></td>
                  </tr>
                  <tr><td><label /></td></tr>
                  <tr>
                    <td></td>
                    <td><button type='submit'>Update</button> <button onClick={this.onClose}>Cancel</button></td>
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

UpdateEntry.propTypes = {
  updateEntry: PropTypes.func,
  loadTableHtml: PropTypes.func,
  table: PropTypes.object
}

function mapStateToProps ({ table }) {
  return { table }
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ updateEntry, loadTableHtml }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToProps)(UpdateEntry)
