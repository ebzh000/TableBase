import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Paper from 'material-ui/Paper';
import RaisedButton from 'material-ui/RaisedButton';
import FlatButton from 'material-ui/FlatButton';
import PersonAdd from 'material-ui/svg-icons/social/person-add';
import TextField from 'material-ui/TextField';
import {Link} from 'react-router';
import ThemeDefault from '../theme-default';
import styles from '../styles';

const LoginPage = () => {
  return (
    <MuiThemeProvider muiTheme={ThemeDefault}>
      <div>
        <div style={styles.loginContainer}>

          <Paper style={styles.paper}>

            <form>
              <TextField
                hintText="Email"
                floatingLabelText="Email"
                fullWidth={true}
              />
              <TextField
                hintText="Password"
                floatingLabelText="Password"
                fullWidth={true}
                type="password"
              />

              <div>
                <Link to="/dashboard">
                  <RaisedButton label="Login"
                                primary={true}
                                style={styles.loginBtn}/>
                </Link>
              </div>
            </form>
          </Paper>

          <div style={styles.buttonsDiv}>
            <FlatButton
              label="Register"
              href="/register"
              style={styles.flatButton}
              icon={<PersonAdd />}
            />
          </div>
        </div>
      </div>
    </MuiThemeProvider>
  );
};

export default LoginPage;
