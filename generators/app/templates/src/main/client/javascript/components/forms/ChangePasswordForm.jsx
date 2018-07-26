import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { required } from 'redux-form-validators';

// eslint-disable-next-line react/prop-types
const ChangePasswordForm = ({ error, handleSubmit }) => (
  <form onSubmit={handleSubmit} noValidate>
    {error && <p style={{ color: 'red' }}>{error}</p>}
    <Field
      name="password"
      component={TextField}
      placeholder="New password"
      label="Password"
      type="password"
      margin="normal"
      validate={[
        required({ msg: 'Password is required' }),
      ]}
      fullWidth
      autoFocus
    />
  </form>
);

export default reduxForm({ form: 'changePassword' })(ChangePasswordForm);
