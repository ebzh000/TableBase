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
import ThemeDefault from '../style/theme-default';
import styles from '../style/styles';

class LoginPage extends Component {
  constructor (props) {
    super(props);
    this.onEmailChange = this.onEmailChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
    this.onFormSubmit = this.onFormSubmit.bind(this)
  }

  onEmailChange (text) {
    this.props.emailChanged(text)
  }

  onPasswordChange (text) {
    this.props.passwordChanged(text)
  }

  onFormSubmit () {
    const { email, password } = this.props;

    // Login
    this.props.login({ email, password });
  }

  render() {
    return (
      <MuiThemeProvider muiTheme={ThemeDefault}>
        <div>
          <div style={styles.loginContainer}>

            <Paper style={styles.paper}>
              <Label style={styles.errorTextStyle}>
                {this.props.error}
              </Label>
              <form onSubmit={this.onFormSubmit}>
                <TextField
                  hintText="Email"
                  floatingLabelText="Email"
                  fullWidth={true}
                  value={this.props.email}
                  onChange={this.onEmailChange}
                />
                <TextField
                  hintText="Password"
                  floatingLabelText="Password"
                  fullWidth={true}
                  type="password"
                  value={this.props.password}
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
  emailChanged: PropTypes.func,
  passwordChanged: PropTypes.func,
  login: PropTypes.func,
  email: PropTypes.string,
  password: PropTypes.string,
  error: PropTypes.string,
  loading: PropTypes.bool
};

const mapStateToProps = ({ auth }) => {
  const { email, password, error, loading } = auth;
  return {
    email,
    password,
    error,
    loading
  }
};

export default connect(mapStateToProps, {
  login
})(LoginPage)
