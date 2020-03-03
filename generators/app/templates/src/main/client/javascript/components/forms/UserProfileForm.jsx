import { Button } from '@material-ui/core';
import { array, bool, func } from 'prop-types';
import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { email, required } from 'redux-form-validators';
import { TextField } from '../wrappers/material';
import MultiSelect from './MultiSelect';

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
    <Field name="roles" component={MultiSelect} options={roles} label="Roles" margin="normal" />

    <div className="actions">
      <Button
        variant="outlined"
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
