import { Button, Chip, Table, TableBody, TableCell, TableHead, TableRow } from '@material-ui/core';
import SendIcon from '@material-ui/icons/Send';
import { arrayOf, array } from 'prop-types';
import React, { Component } from 'react';
import { Link } from 'react-router';
import { connect } from 'react-redux';
import Alert from 'react-s-alert';
import { SubmissionError } from 'redux-form';
import AppPropTypes from '../../components/AppPropTypes';
import FormDialog from '../../components/forms/FormDialog';
import InviteUserForm from '../../components/forms/InviteUserForm';
import { getReferenceDataRoles } from '../../reducers';
import api from '../../services/api';
import './ManageUsersPage.less';

const UserTable = ({ users }) => (
  <Table className="user-table">
    <TableHead>
      <TableRow>
        <TableCell>Email</TableCell>
        <TableCell>First name</TableCell>
        <TableCell>Last name</TableCell>
        <TableCell>Roles</TableCell>
        <TableCell>Status</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {users.map(user => (
        <TableRow key={user.id}>
          <TableCell>
            <Link to={`/admin/users/${user.id}`}>{user.email}</Link>
          </TableCell>
          <TableCell>{user.firstName}</TableCell>
          <TableCell>{user.lastName}</TableCell>
          <TableCell>
            <div className="roles">
              {user.roles.map(role => <Chip key={role} className="role" label={role} />)}
            </div>
          </TableCell>
          <TableCell>{user.status}</TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: arrayOf(AppPropTypes.user).isRequired,
};

class ManageUsersPage extends Component {
  static propTypes = {
    roles: array.isRequired,
  };

  state = { inviteUserDialogOpen: false, users: [] };

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers() {
    api.users.list()
      .then(users => this.setState({ users }))
      .catch(error => Alert.error(`Error fetching users: ${error.message}`));
  }

  handleInviteUser = values => api.users.invite(values)
    .then(() => {
      Alert.success('User invite sent!');
      this.closeInviteUserDialog();
    })
    .catch((error) => {
      throw new SubmissionError({ _error: error.message });
    });

  openInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: true });
  };

  closeInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: false });
  };

  render() {
    const { inviteUserDialogOpen, users } = this.state;
    const { roles } = this.props;

    return (
      <div className="manage-users-page">
        <h1 className="display-1">Manage users</h1>

        <Button
          variant="raised"
          className="invite-user-btn"
          onClick={this.openInviteUserDialog}
        >
          Invite User
          <SendIcon className="invite-user-btn-icon"/>
        </Button>

        <UserTable users={users} />

        <FormDialog
          title="Invite user"
          submitButtonText="Invite"
          formComponent={InviteUserForm}
          formName="inviteUser"
          open={inviteUserDialogOpen}
          onCancel={this.closeInviteUserDialog}
          onSubmit={this.handleInviteUser}
          roles={roles}
        />
      </div>
    );
  }
}

const mapStateToProps = state => ({
  roles: getReferenceDataRoles(state).map(input => ({
    value: input.id,
    label: input.description,
  })),
});

export default connect(mapStateToProps)(ManageUsersPage);
