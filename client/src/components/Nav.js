import React, {Component, PropTypes} from 'react';
import {Link} from 'react-router';
import { logout } from '../actions/AuthActions';
import LoadingButton from './LoadingButton';

class Nav extends Component {
    render() {
        // Render either the Log In and register buttons, or the logout button
        // based on the current authentication state.
        const navButtons = this.props.loggedIn ? (
            <div>
                <Link to="/dashboard" className="btn btn--dash btn--nav">Dashboard</Link>
                {this.props.currentlySending ? (
                    <LoadingButton className="btn--nav"/>
                ) : (
                    <Link to="/" className="btn btn--login btn--nav" onClick={::this._logout}>Logout</Link>
                )}
            </div>
        ) : (
            <div>
                <Link to="/register" className="btn btn--login btn--nav">Register</Link>
                <Link to="/login" className="btn btn--login btn--nav">Login</Link>
            </div>
        );

        return (
            <div className="nav">
                <div className="nav__wrapper">
                    <Link to="/" className="nav__logo-wrapper"><h1 className="nav__logo">TableBase</h1></Link>
                    {navButtons}
                </div>
            </div>
        );
    }

    _logout() {
        this.props.dispatch(logout());
    }
}

Nav.propTypes = {
    loggedIn: React.PropTypes.bool.isRequired,
    currentlySending: React.PropTypes.bool.isRequired
};

export default Nav;