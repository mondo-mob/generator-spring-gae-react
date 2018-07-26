/* eslint-disable jsx-a11y/no-static-element-interactions, jsx-a11y/click-events-have-key-events */
import Divider from '@material-ui/core/Divider';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import DashboardIcon from '@material-ui/icons/Dashboard';
import ExitIcon from '@material-ui/icons/ExitToApp';
import PeopleIcon from '@material-ui/icons/People';
import PersonIcon from '@material-ui/icons/Person';
import { bool, func, object } from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import * as authActions from '../../../actions/auth';
import { closeDrawer as closeDrawerAction } from '../../../actions/drawer';
import { getLoggedInUser, getOpenDrawer } from '../../../reducers';
import ProfileCard from './ProfileCard';

const MenuDrawer = ({
  loggedInUser,
  logout,
  navigateTo,
  closeDrawer,
  isOpen,
  ...rest
}) => (
  <Drawer onClose={closeDrawer} open={isOpen} {...rest}>
    <div onClick={closeDrawer}>
      <ProfileCard user={loggedInUser}/>

      <Divider/>
      <List>
        <ListItem
          button
          onClick={() => navigateTo('/admin')}
        >
          <ListItemIcon><DashboardIcon/></ListItemIcon>
          <ListItemText primary="Dashboard"/>
        </ListItem>
        <ListItem
          button
          onClick={() => navigateTo('/admin/users')}
        >
          <ListItemIcon><PeopleIcon/></ListItemIcon>
          <ListItemText primary="Manage users"/>
        </ListItem>
      </List>

      <Divider/>
      <ListSubheader>My account</ListSubheader>

      <List>
        <ListItem
          button
          onClick={() => navigateTo(`/admin/users/${loggedInUser.id}`)}
        >
          <ListItemIcon><PersonIcon/></ListItemIcon>
          <ListItemText primary="Profile"/>
        </ListItem>
        <ListItem
          button
          onClick={() => logout()}
        >
          <ListItemIcon><ExitIcon/></ListItemIcon>
          <ListItemText primary="Sign out"/>
        </ListItem>
      </List>
    </div>
  </Drawer>
);

MenuDrawer.propTypes = {
  loggedInUser: object,
  logout: func.isRequired,
  navigateTo: func.isRequired,
  closeDrawer: func.isRequired,
  isOpen: bool.isRequired,
};

MenuDrawer.defaultProps = {
  loggedInUser: {},
};

const mapStateToProps = state => ({
  loggedInUser: getLoggedInUser(state),
  isOpen: getOpenDrawer(state) === 'admin',
});

const mapDispatchToProps = dispatch => ({
  logout: () => dispatch(authActions.logout()).then(() => dispatch(push('/'))),
  navigateTo: path => dispatch(push(path)),
  closeDrawer: () => dispatch(closeDrawerAction()),
});

const mergeProps = (stateProps, dispatchProps, ownProps) => ({
  ...stateProps,
  ...dispatchProps,
  ...ownProps,
  navigateTo: (path) => {
    dispatchProps.navigateTo(path);
  },
});

export default connect(mapStateToProps, mapDispatchToProps, mergeProps)(MenuDrawer);
