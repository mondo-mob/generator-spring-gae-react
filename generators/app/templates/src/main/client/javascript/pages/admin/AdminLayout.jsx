import React, { Component } from 'react';
import { arrayOf, node, oneOfType } from 'prop-types';
import { AppBar } from 'material-ui';
import MenuDrawer from './menu/MenuDrawer';

class AdminLayout extends Component {
  static propTypes = {
    children: oneOfType([node, arrayOf(node)]).isRequired,
  };

  static defaultProps = {
    loggedInUser: {},
  };

  constructor(props) {
    super(props);

    this.state = { drawerOpen: false };
  }

  toggleDrawer = () => {
    this.setState({ drawerOpen: !this.state.drawerOpen });
  };

  render() {
    const { drawerOpen } = this.state;
    const { children } = this.props;

    return (
      <div className="admin-layout">
        <AppBar
          title="Test React"
          onLeftIconButtonTouchTap={this.toggleDrawer}
        />

        <MenuDrawer
          open={drawerOpen}
          onRequestChange={open => this.setState({ drawerOpen: open })}
        />

        <div className="main">
          { children }
        </div>
      </div>
    );
  }
}

export default AdminLayout;
