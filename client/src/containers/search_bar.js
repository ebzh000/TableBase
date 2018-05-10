import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { searchTable } from '../actions/index'

class SearchBar extends Component {
  constructor (props) {
    super(props)

    this.state = { term: '' }

    this.onInputChange = this.onInputChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
  }

  onInputChange (event) {
    this.setState({ term: event.target.value })
  }

  onFormSubmit (event) {
    event.preventDefault()

    // Fetch table data
    this.props.searchTable(this.state.term)
    this.setState({ term: '' })
  }

  render () {
    return (
      <form onSubmit={this.onFormSubmit} className='input-group'>
        <input
          placeholder='Search for all the tables...'
          className='form-control'
          value={this.state.term}
          onChange={this.onInputChange}
        />
        <span className='input-group-btn'>
          <button type='submit' className='btn btn-secondary'>
            Sumbit
          </button>
        </span>
      </form>
    )
  }
}

SearchBar.propTypes = {
  searchTable: PropTypes.func
}

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ searchTable }, dispatch)
}

export default connect(null, mapDispatchToProps)(SearchBar)
