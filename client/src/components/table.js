import React, { Component } from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'

class Table extends Component {
  render () {
    const tableHtml = this.props.tableHtml
    return (
      <div>
        <h2 className='pad-top-5'>{this.props.table.tableName}</h2>
        <br />
        <div dangerouslySetInnerHTML={{__html: tableHtml}} />
      </div>
    )
  }
}

Table.propTypes = {
  tableHtml: PropTypes.string,
  table: PropTypes.object
}

function mapStateToProps ({ tableHtml, table }) {
  return { tableHtml, table }
}

export default connect(mapStateToProps, null)(Table)
