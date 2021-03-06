import { AppBar, IconButton, Toolbar, Typography } from '@material-ui/core';
import MenuIcon from '@material-ui/icons/Menu';
import { arrayOf, func, node, oneOfType } from 'prop-types';
import React from 'react';
import { connect } from 'react-redux';
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import 'react-s-alert/dist/s-alert-default.css';
import { openDrawer as openDrawerAction } from '../../actions/drawer';
import MenuDrawer from './menu/MenuDrawer';

const AdminLayout = ({ children, openDrawer }) => (
  <div className="admin-layout">
    <Alert effect="slide" position="bottom-right" stack/>

    <AppBar position="static">
      <Toolbar>
        <IconButton aria-label="Menu" color="inherit" onClick={openDrawer}>
          <MenuIcon/>
        </IconButton>
        <Typography variant="subtitle1" color="inherit">
          Administration
        </Typography>
      </Toolbar>
    </AppBar>

    <MenuDrawer/>

    <div className="main">{children}</div>
  </div>
);

AdminLayout.propTypes = {
  children: oneOfType([node, arrayOf(node)]).isRequired,
  openDrawer: func.isRequired,
};

export const mapDispatchToProps = (dispatch) => ({
  openDrawer: () => dispatch(openDrawerAction('admin')),
});

export default connect(null, mapDispatchToProps)(AdminLayout);
