import React, {Component} from 'react';
import {Link} from 'react-router';
import {connect} from 'react-redux';

class HomePage extends Component {
    render() {
        const dispatch = this.props.dispatch;
        const {loggedIn} = this.props.data;

        return (
            <article>
                <div>
                    <section className="text-section" style={{textAlign:"center", float:"center"}}>
                        {/* Change the copy based on the authentication status */}
                        {loggedIn ? (
                            <h1>Welcome to TableBase, you are logged in!</h1>
                        ) : (
                            <h1>Welcome to TableBase!</h1>
                        )}
                        <p>TableBase is a web application that enables users to create, edit and format tables.</p>
                        <br/>
                        {loggedIn ? (
                            <Link to="/dashboard" className="btn btn--dash">Dashboard</Link>
                        ) : (
                            <div>
                                <Link to="/login" className="btn btn--login">Login</Link>
                                <Link to="/register" className="btn btn--register">Register</Link>
                            </div>
                        )}
                    </section>
                </div>
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
export default connect(select)(HomePage);
