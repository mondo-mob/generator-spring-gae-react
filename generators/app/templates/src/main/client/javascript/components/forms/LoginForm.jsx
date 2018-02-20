import React, { Component } from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import RaisedButton from 'material-ui/RaisedButton';
import PropTypes from 'prop-types';
import { email, required } from './validators';

class LoginForm extends Component {
  static propTypes = {
    handleSubmit: PropTypes.func.isRequired,
    submitting: PropTypes.bool.isRequired,
  };

  render() {
    const { handleSubmit, submitting } = this.props;

    return (
      <form onSubmit={handleSubmit} noValidate>
        <Field
          name="username"
          component={TextField}
          hintText="Your email address"
          floatingLabelText="Email"
          type="email"
          validate={[required('Email address is required'), email('Enter a valid email')]}
          fullWidth
        />

        <Field
          name="password"
          hintText="Your password"
          floatingLabelText="Password"
          component={TextField}
          type="password"
          validate={required('Password is required')}
          fullWidth
        />

        <div className="actions">
          <RaisedButton
            label={submitting ? 'Signing in...' : 'Sign in'}
            type="submit"
            disabled={submitting}
            primary
            fullWidth
          />
        </div>
      </form>
    );
  }
}

export default reduxForm({ form: 'login' })(LoginForm);
