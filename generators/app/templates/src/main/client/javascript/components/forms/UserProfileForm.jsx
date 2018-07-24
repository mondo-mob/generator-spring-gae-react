import { Button } from '@material-ui/core';
import { bool, func, array } from 'prop-types';
import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { email, length, required } from 'redux-form-validators';
import MenuItem from '@material-ui/core/MenuItem';

const UserProfileForm = ({ handleSubmit, submitting, roles }) => (
  <form onSubmit={handleSubmit}>
    <Field
      name="name"
      component={TextField}
      placeholder="Your full name"
      label="Full name"
      margin="normal"
      validate={required('Your full name is required')}
      fullWidth
    />
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
    />
    <Field
      name="roles"
      component={TextField}
      label="Roles"
      select
      SelectProps={{ multiple: true }}
      fullWidth
      validate={[
        length({ min: 1, message: 'At least one role must be selected' }),
      ]}
    >
      {roles.map(role => (
        <MenuItem key={role.value} value={role.value}>{role.label}</MenuItem>
      ))}
    </Field>

    <div className="actions">
      <Button
        variant="raised"
        type="submit"
        disabled={submitting}
        color="primary"
      >
        {submitting ? 'Saving...' : 'Save'}
      </Button>
    </div>
  </form>
);

UserProfileForm.propTypes = {
  handleSubmit: func.isRequired,
  submitting: bool.isRequired,
  roles: array.isRequired,
};

export default reduxForm({ form: 'userProfile' })(UserProfileForm);
