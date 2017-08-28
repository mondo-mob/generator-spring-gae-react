import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { func, object } from 'prop-types';
import { Divider, Drawer, MenuItem, Subheader } from 'material-ui';
import ExitIcon from 'material-ui/svg-icons/action/exit-to-app';
import DashboardIcon from 'material-ui/svg-icons/action/dashboard';
import PersonIcon from 'material-ui/svg-icons/social/person';
import PeopleIcon from 'material-ui/svg-icons/social/people';
import ProfileCard from './ProfileCard';
import { getLoggedInUser } from '../../../reducers';
import api from '../../../services/api';

class MenuDrawer extends Component {
  static propTypes = {
    loggedInUser: object,
    logout: func.isRequired,
    navigateTo: func.isRequired,
    onRequestChange: func,
  };

  static defaultProps = {
    loggedInUser: {},
    onRequestChange: () => {},
  };

  render() {
    const { loggedInUser, logout, navigateTo, onRequestChange, ...rest } = this.props;

    return (
      <Drawer
        {...rest}
        docked={false}
        width={280}
        onRequestChange={onRequestChange}
      >
        <ProfileCard user={loggedInUser} />

        <MenuItem
          primaryText="Dashboard"
          leftIcon={<DashboardIcon />}
          onClick={() => navigateTo('/admin')}
        />
        <MenuItem
          primaryText="Manage users"
          leftIcon={<PeopleIcon />}
          onClick={() => navigateTo('/admin/users')}
        />

        <Divider />
        <Subheader>Me</Subheader>

        <MenuItem
          primaryText="Profile"
          leftIcon={<PersonIcon />}
          onClick={() => navigateTo(`/admin/users/${loggedInUser.username}`)}
        />
        <MenuItem
          primaryText="Sign out"
          leftIcon={<ExitIcon />}
          onClick={() => logout()}
        />
      </Drawer>
    );
  }
}

const mapStateToProps = state => ({
  loggedInUser: getLoggedInUser(state),
});

const mapDispatchToProps = dispatch => ({
  logout: () => api.users.logout().then(() => dispatch(push('/'))),
  navigateTo: path => dispatch(push(path)),
});

export default connect(mapStateToProps, mapDispatchToProps)(MenuDrawer);
