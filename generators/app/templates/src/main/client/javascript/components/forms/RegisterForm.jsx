import { Button } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { required } from 'redux-form-validators';
import { TextField } from '../wrappers/material';

const RegisterForm = ({ handleSubmit, submitting }) => (
  <form onSubmit={handleSubmit} noValidate>
    <Field
      name="firstName"
      margin="normal"
      component={TextField}
      placeholder="Your first name"
      label="First name"
      validate={required({ msg: 'Your first name is required' })}
      fullWidth
    />
    <Field
      name="lastName"
      margin="normal"
      component={TextField}
      placeholder="Your last name"
      label="Last name"
      validate={required({ msg: 'Your last name is required' })}
      fullWidth
    />
    <Field
      name="password"
      margin="normal"
      placeholder="Choose your password"
      label="Choose Password"
      component={TextField}
      type="password"
      validate={required({ msg: 'Password is required' })}
      fullWidth
    />

    <div className="actions">
      <Button
        variant="contained"
        type="submit"
        disabled={submitting}
        color="primary"
        fullWidth
      >
        {submitting ? 'Completing setup...' : 'Complete setup'}
      </Button>
    </div>
  </form>
);

RegisterForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired,
  submitting: PropTypes.bool.isRequired,
};

export default reduxForm({ form: 'register' })(RegisterForm);
