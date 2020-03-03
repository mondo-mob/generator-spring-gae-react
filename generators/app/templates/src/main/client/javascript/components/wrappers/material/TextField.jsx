import React from 'react';
import { TextField } from '@material-ui/core';
import * as PropTypes from 'prop-types';

// From https://redux-form.com/8.2.2/examples/material-ui/
const Field = ({ label, input, meta: { touched, invalid, error } = {}, ...custom }) => (
  <TextField label={label} placeholder={label} error={touched && invalid} helperText={touched && error} {...input} {...custom} />
);

Field.propTypes = {
  input: PropTypes.any.isRequired,
  label: PropTypes.string,
  meta: PropTypes.object,
};

Field.defaultProps = {
  label: undefined,
  meta: {},
};

export default Field;
