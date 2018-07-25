import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { email, length, required } from 'redux-form-validators';
import MenuItem from '@material-ui/core/MenuItem';

// eslint-disable-next-line react/prop-types
const InviteUserForm = ({ error, roles, handleSubmit }) => (
  <form id="dialog-form" onSubmit={handleSubmit} noValidate>
    {error && <p style={{ color: 'red' }}>{error}</p>}
    <Field
      name="email"
      component={TextField}
      placeholder="Your email address"
      label="Email"
      type="email"
      margin="normal"
      validate={[
        required({ msg: 'Email address is required' }),
        email({ msg: 'Enter a valid email' }),
      ]}
      fullWidth
      autoFocus
    />
    <Field
      name="roles"
      component={TextField}
      label="Roles"
      select
      SelectProps={{ multiple: true }}
      fullWidth
      format={value => (Array.isArray(value) ? value : [])}
      validate={[
        length({ min: 1, message: 'At least one role must be selected' }),
      ]}
    >
      {roles.map(role => (
        <MenuItem key={role.value} value={role.value}>{role.label}</MenuItem>
      ))}
    </Field>
  </form>
);

export default reduxForm({ form: 'inviteUser' })(InviteUserForm);
