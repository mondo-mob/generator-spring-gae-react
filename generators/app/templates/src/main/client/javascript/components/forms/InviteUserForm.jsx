import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { email, required } from 'redux-form-validators';
import ChipSelectField from '../common/ChipSelectField';

// eslint-disable-next-line react/prop-types
const InviteUserForm = ({ error, roles, handleSubmit }) => (
  <form id="dialog-form" onSubmit={handleSubmit}>
    {error && <p style={{ color: 'red' }}>{error}</p>}
    <Field
      name="email"
      component={TextField}
      placeholder="Your email address"
      label="Email"
      type="email"
      margin="normal"
      validate={[
        required('Email address is required'),
        email('Enter a valid email'),
      ]}
      fullWidth
      autoFocus
    />
    <Field
      name="roles"
      component={ChipSelectField}
      options={roles}
      label="Roles"
      placeholder="Start typing roles..."
      margin="normal"
      fullWidth
      multi
      removeSelected
      clearable={false}
    />
  </form>
);

export default reduxForm({ form: 'inviteUser' })(InviteUserForm);
