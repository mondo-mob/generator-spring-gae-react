import { intersection } from 'lodash';
import { fetchUser } from '../actions/auth';
import { getLoggedInUser, getIsAuthenticated } from '../reducers';
import store from '../store';
import { fetchReferenceData } from '../actions/referenceData';

const { dispatch, getState } = store;

const withLogin = nextPage => ({ pathname: '/login', search: nextPage === '/' ? null : `?next=${nextPage}` });

export const loginRequired = (push, location, callback) => {
  dispatch(fetchUser()).then(() => {
    if (getIsAuthenticated(getState())) {
      callback();
    } else {
      dispatch(push(withLogin(location.pathname)));
      callback(new Error('Login required'));
    }
  }, (error) => {
    dispatch(push(withLogin(location.pathname)));
    callback(error);
  });
};

export const loadRefData = (push, location, callback) => {
  dispatch(fetchReferenceData()).then(() => {
    callback();
  });
};

export const hasAnyRole = (...roles) => (push, location, callback) => {
  const user = getLoggedInUser(getState());

  const hasRequiredRole = user && intersection(roles, user.roles).length > 0;
  if (hasRequiredRole) {
    callback();
    return;
  }

  dispatch(push('/'));
  callback(new Error('User lacks required role'));
};

export const isAdminUser = hasAnyRole('ADMIN', 'SUPER');
