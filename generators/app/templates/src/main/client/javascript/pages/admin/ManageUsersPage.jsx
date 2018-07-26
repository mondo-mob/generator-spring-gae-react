import Button from '@material-ui/core/Button';
import Chip from '@material-ui/core/Chip';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import SendIcon from '@material-ui/icons/Send';
import { func, array, arrayOf } from 'prop-types';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import Alert from 'react-s-alert';
import { SubmissionError } from 'redux-form';

import AppPropTypes from '../../components/AppPropTypes';
import FormDialog from '../../components/forms/FormDialog';
import InviteUserForm from '../../components/forms/InviteUserForm';
import ChangePasswordForm from '../../components/forms/ChangePasswordForm';
import { getReferenceDataRoles } from '../../reducers';
import api from '../../services/api';
import './ManageUsersPage.less';

const UserTable = ({ users, onChangePassword }) => (
  <Table className="user-table">
    <TableHead>
      <TableRow>
        <TableCell>Email</TableCell>
        <TableCell>First name</TableCell>
        <TableCell>Last name</TableCell>
        <TableCell>Roles</TableCell>
        <TableCell>Status</TableCell>
        <TableCell/>
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
              {user.roles.map(role => <Chip key={role} className="role" label={role}/>)}
            </div>
          </TableCell>
          <TableCell>{user.status}</TableCell>
          <TableCell>
            <Button
              onClick={() => onChangePassword(user.id)}
            >
              Change password
            </Button>
          </TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: arrayOf(AppPropTypes.user).isRequired,
  onChangePassword: func.isRequired,
};

class ManageUsersPage extends Component {
  static propTypes = {
    roles: array.isRequired,
  };

  state = { inviteUserDialogOpen: false, changePasswordDialogUserId: null, users: [] };

  componentDidMount() {
    this.fetchUsers();
  }

  handleInviteUser = values => api.users.invite(values)
    .then(() => {
      Alert.success('User invite sent!');
      this.closeInviteUserDialog();
    })
    .catch((error) => {
      throw new SubmissionError({ _error: error.message });
    });

  handleChangePassword = (values) => {
    const { changePasswordDialogUserId } = this.state;
    api.users.changePassword(changePasswordDialogUserId, values.password)
      .then(() => {
        Alert.success('Password changed');
        this.closeChangePasswordDialog();
      })
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });
  };

  openInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: true });
  };

  closeInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: false });
  };

  openChangePasswordDialog = (userId) => {
    this.setState({ changePasswordDialogUserId: userId });
  };

  closeChangePasswordDialog = () => {
    this.setState({ changePasswordDialogUserId: null });
  };

  fetchUsers() {
    api.users.list()
      .then(users => this.setState({ users }))
      .catch(error => Alert.error(`Error fetching users: ${error.message}`));
  }

  render() {
    const { inviteUserDialogOpen, changePasswordDialogUserId, users } = this.state;
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

        <UserTable users={users} onChangePassword={this.openChangePasswordDialog}/>

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

        <FormDialog
          title="Change password"
          submitButtonText="Change"
          formComponent={ChangePasswordForm}
          formName="changePassword"
          open={!!changePasswordDialogUserId}
          onCancel={this.closeChangePasswordDialog}
          onSubmit={this.handleChangePassword}
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
