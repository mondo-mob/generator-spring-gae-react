import React from 'react';
import * as PropTypes from 'prop-types';
import { FormControlLabel, Switch } from '@material-ui/core';

const Field = ({ input, label }) => (
  <div>
    <FormControlLabel control={<Switch checked={!!input.value} onChange={input.onChange} />} label={label} />
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
