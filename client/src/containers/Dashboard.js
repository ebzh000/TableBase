import React, {Component} from 'react';
import {connect} from 'react-redux';

class Dashboard extends Component {
    render() {
        return (
            <article>
                <section className="text-section">
                    <h1>Dashboard</h1>
                    <p>Welcome, you are logged in!</p>
                    <p>This page is yet to be implemented</p>
                    <ol>
                        <li>Open Table</li>
                        <li>Search Table</li>
                        <li>Load Table</li>
                    </ol>
                </section>
            </article>
        );
    }
}

// Which props do we want to inject, given the global state?
function select(state) {
    return {
        data: state
    };
}

// Wrap the component to inject dispatch and state into it
export default connect(select)(Dashboard);