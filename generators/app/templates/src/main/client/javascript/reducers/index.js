import { combineReducers } from 'redux';
import { reducer as form } from 'redux-form';
import admin, * as fromAdmin from './admin';
import auth, * as fromAuth from './auth';
import users, * as fromUsers from './users';
import drawer, * as fromDrawer from './drawer';
import referenceData, * as fromReferenceData from './referenceData';

/**
 * Root reducer for the app.
 */
const rootReducer = combineReducers({
  form,
  auth,
  admin,
  users,
  drawer,
  referenceData,
});

export const getLoggedInUser = state =>
  fromAuth.getUser(state.auth);

export const getIsAuthenticated = state =>
  fromAuth.getIsAuthenticated(state.auth);

export const isInviteUserDialogOpen = state => fromAdmin.isInviteUserDialogOpen(state.admin);
export const getUsers = state => fromUsers.getAll(state.users);

export const getOpenDrawer = state =>
  fromDrawer.getOpenDrawer(state.drawer);

export const getReferenceDataRoles = state => fromReferenceData.getAll(state.referenceData, 'Role');

export default rootReducer;
