import React from 'react';
import { Route, Switch } from 'react-router-dom';
import AdminLayout from '../pages/admin/AdminLayout';
import DashboardPage from '../pages/admin/DashboardPage';
import ManageUsersPage from '../pages/admin/ManageUsersPage';
import UserProfilePage from '../pages/admin/UserProfilePage';
import AsyncComponent from './AsyncComponent';
import { isAdminUser, loadRefData, loginRequired } from './hooks';

const AdminRoutes = () => (
  <AsyncComponent asyncActions={[loginRequired, isAdminUser, loadRefData]}>
    <AdminLayout>
      <Switch>
        <Route exact path="/admin" component={DashboardPage} />
        <Route exact path="/admin/users" component={ManageUsersPage} />
        <Route exact path="/admin/users/:userId" component={UserProfilePage} />
      </Switch>
    </AdminLayout>
  </AsyncComponent>
);

export default AdminRoutes;
