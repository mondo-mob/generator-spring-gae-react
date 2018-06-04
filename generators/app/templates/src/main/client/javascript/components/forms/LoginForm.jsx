import Button from '@material-ui/core/Button';
import PropTypes from 'prop-types';
import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { email, required } from 'redux-form-validators';
import FormError from './FormError';

const LoginForm = ({ handleSubmit, submitting, error }) => (
  <form onSubmit={handleSubmit} noValidate>
    <FormError value={error}/>
    <Field
      name="username"
      component={TextField}
      placeholder="Your email address"
      label="Email"
      type="email"
      margin="dense"
      validate={[required({ msg: 'Email address is required' }), email({ msg: 'Enter a valid email' })]}
      fullWidth
    />

    <Field
      name="password"
      placeholder="Your password"
      label="Password"
      component={TextField}
      type="password"
      margin="dense"
      validate={required({ msg: 'Password is required' })}
      fullWidth
    />

    <div className="actions">
      <Button
        variant="raised"
        color="primary"
        type="submit"
        disabled={submitting}
        fullWidth
      >
        {submitting ? 'Signing in...' : 'Sign in'}
      </Button>
    </div>
  </form>
);

LoginForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired,
  submitting: PropTypes.bool.isRequired,
  error: PropTypes.any,
};

LoginForm.defaultProps = {
  error: undefined,
};

export default reduxForm({ form: 'login' })(LoginForm);
