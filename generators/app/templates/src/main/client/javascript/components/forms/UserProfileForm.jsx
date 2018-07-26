import Button from '@material-ui/core/Button';
import { array, bool, func } from 'prop-types';
import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { email, length, required } from 'redux-form-validators';
import MenuItem from '@material-ui/core/MenuItem';

const UserProfileForm = ({ handleSubmit, submitting, roles }) => (
  <form onSubmit={handleSubmit} noValidate>
    <Field
      name="firstName"
      component={TextField}
      placeholder="Your first name"
      label="First name"
      margin="normal"
      validate={required({ msg: 'Your first name is required' })}
      fullWidth
    />
    <Field
      name="lastName"
      component={TextField}
      placeholder="Your last name"
      label="Last name"
      margin="normal"
      validate={required({ msg: 'Your last name is required' })}
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
        required({ msg: 'Email address is required' }),
        email({ msg: 'Enter a valid email' }),
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
