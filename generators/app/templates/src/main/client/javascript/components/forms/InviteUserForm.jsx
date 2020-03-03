import * as PropTypes from 'prop-types';
import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { email, required } from 'redux-form-validators';
import { TextField } from '../wrappers/material';
import MultiSelect from './MultiSelect';
import { makeStyles } from '@material-ui/core';


const useStyles = makeStyles(() => ({
  root: {
    minHeight: 300,
  },
}));

const InviteUserForm = ({ error, roles, handleSubmit }) => {
  const classes = useStyles();
  return (
    <form className={classes.root} onSubmit={handleSubmit}>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <Field
        name="email"
        component={TextField}
        placeholder="Your email address"
        label="Email"
        type="email"
        margin="normal"
        validate={[required('Email address is required'), email('Enter a valid email')]}
        fullWidth
      />
      <Field name="roles" component={MultiSelect} options={roles} label="Roles" margin="normal"/>
    </form>
  );
};

InviteUserForm.propTypes = {
  roles: PropTypes.array.isRequired,
  handleSubmit: PropTypes.func.isRequired,
  error: PropTypes.string,
};

InviteUserForm.defaultProps = {
  error: undefined,
};

export default reduxForm({ form: 'inviteUser' })(InviteUserForm);
