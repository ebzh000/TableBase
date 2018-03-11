import React, { PropTypes, Component } from 'react'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { login } from '../actions/AuthActions'

import Paper from 'material-ui/Paper';
import RaisedButton from 'material-ui/RaisedButton';
import FlatButton from 'material-ui/FlatButton';
import PersonAdd from 'material-ui/svg-icons/social/person-add';
import TextField from 'material-ui/TextField';
import {Link} from 'react-router';
import ThemeDefault from '../theme-default';
import styles from '../styles';

class LoginPage extends Component {
  constructor (props) {
    super(props);

    this.state = { email: '', password: ''};

    this.onEmailChange = this.onEmailChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this)
  }

  onEmailChange (event) {
    this.setState({ email: event.target.value })
  }

  onPasswordChange (event) {
    this.setState({ password: event.target.value })
  }

  onFormSubmit (event) {
    event.preventDefault();

    // Login
    this.props.login(this.state.email, this.state.password);
    this.setState({ email: '', password: ''})
  }

  render() {
    return (
      <MuiThemeProvider muiTheme={ThemeDefault}>
        <div>
          <div style={styles.loginContainer}>

            <Paper style={styles.paper}>

              <form onSubmit={this.onFormSubmit}>
                <TextField
                  hintText="Email"
                  floatingLabelText="Email"
                  fullWidth={true}
                  value={this.state.email}
                  onChange={this.onEmailChange}
                />
                <TextField
                  hintText="Password"
                  floatingLabelText="Password"
                  fullWidth={true}
                  type="password"
                  value={this.state.password}
                  onChange={this.onPasswordChange}
                />

                <div>
                  <Link to="/dashboard">
                    <RaisedButton
                      label="Login"
                      primary={true}
                      style={styles.loginBtn}
                      type="submit"
                    />
                  </Link>
                </div>
              </form>
            </Paper>

            <div style={styles.buttonsDiv}>
              <FlatButton
                label="Register"
                href="/register"
                style={styles.flatButton}
                icon={<PersonAdd/>}
              />
            </div>
          </div>
        </div>
      </MuiThemeProvider>
    );
  }
}

LoginPage.propTypes = {
  login: PropTypes.func
};

function mapDispatchToProps (dispatch) {
  return bindActionCreators({ login }, dispatch)
}

export default connect(null, mapDispatchToProps)(LoginPage)
