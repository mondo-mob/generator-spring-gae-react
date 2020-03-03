import React from 'react';
import * as PropTypes from 'prop-types';
import { Checkbox, FormControlLabel } from '@material-ui/core';

// From https://redux-form.com/8.2.2/examples/material-ui/
const Field = ({ input, label }) => (
  <div>
    <FormControlLabel control={<Checkbox checked={!!input.value} onChange={input.onChange} />} label={label} />
  </div>
);

Field.propTypes = {
  input: PropTypes.any.isRequired,
  label: PropTypes.string,
};

Field.defaultProps = {
  label: undefined,
};

export default Field;
