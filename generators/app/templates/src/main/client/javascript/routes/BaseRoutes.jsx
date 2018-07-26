import React from 'react';
import { Route, Switch } from 'react-router-dom';
import { ConnectedRouter } from 'connected-react-router';
import EmptyLayout from '../pages/EmptyLayout';
import HomePage from '../pages/HomePage';
import LoginPage from '../pages/LoginPage';
import NotFoundPage from '../pages/NotFoundPage';
import RegisterPage from '../pages/RegisterPage';
import { history } from '../store';
import AdminRoutes from './AdminRoutes';
import AsyncComponent from './AsyncComponent';
import { loadRefData } from './hooks';

const HomeRoutes = () => (
  <EmptyLayout>
    <AsyncComponent asyncActions={[loadRefData]}>
      <Switch>
        <Route path="/" component={HomePage} />
      </Switch>
    </AsyncComponent>
  </EmptyLayout>
);

const BaseRoutes = () => (
  <ConnectedRouter history={history}>
    <Switch>
      <Route path="/admin" component={AdminRoutes} />
      <Route exact path="/login" component={LoginPage} />
      <Route exact path="/register/:inviteCode" component={RegisterPage} />
      <Route exact path="/" component={HomeRoutes} />
      <Route path="*" component={NotFoundPage} />
    </Switch>
  </ConnectedRouter>
);

export default BaseRoutes;
