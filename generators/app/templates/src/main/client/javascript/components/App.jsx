import React from 'react';
import { CssBaseline } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/core/styles';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import BaseRoutes from '../routes/BaseRoutes';
import store from '../store';
import theme from '../theme';
import '../../less/styles/main.less';

const App = () => (
  <ThemeProvider theme={theme}>
    <Provider store={store}>
      <IntlProvider locale="en-AU">
        <>
          <CssBaseline />
          <BaseRoutes />
        </>
      </IntlProvider>
    </Provider>
  </ThemeProvider>
);

export default App;
