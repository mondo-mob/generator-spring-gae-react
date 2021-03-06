/* eslint-disable react/no-array-index-key */
import { withStyles } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';
import * as model from '../../model';

const styles = ({ palette: { error } }) => ({
  formError: {
    marginTop: '0.1rem',
    color: error.main,
    fontWeight: 500,
  },
});

const FormError = ({ value, classes }) => {
  const errors = (value && value.messages) || (value && [value]);
  return (
    <div>{errors && errors.map((err, index) => <p key={index} className={classes.formError}>{err}</p>)}</div>
  );
};

FormError.propTypes = {
  classes: PropTypes.object.isRequired,
  value: PropTypes.oneOfType([PropTypes.string, model.error]),
};

FormError.defaultProps = {
  value: undefined,
};

export default withStyles(styles)(FormError);
