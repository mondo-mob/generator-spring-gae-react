import CssBaseline from '@material-ui/core/CssBaseline';
import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';
import React, { Fragment } from 'react';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import BaseRoutes from '../routes/BaseRoutes';
import store from '../store';
import theme from '../theme';

const App = () => (
  <Fragment>
    <Provider store={store}>
      <IntlProvider locale="en-AU">
        <MuiThemeProvider theme={theme}>
          <CssBaseline />
          <BaseRoutes />
        </MuiThemeProvider>
      </IntlProvider>
    </Provider>
  </Fragment>);

export default App;
