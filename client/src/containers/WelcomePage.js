import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import ThemeDefault from '../theme-default';

const WelcomePage = () => {

  return (
    <MuiThemeProvider muiTheme={ThemeDefault}>
      <p>Test Welcome Page</p>
    </MuiThemeProvider>
  );
};

export default WelcomePage;
