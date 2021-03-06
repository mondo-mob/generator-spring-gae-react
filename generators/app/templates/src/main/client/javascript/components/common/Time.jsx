import React from 'react';
import PropTypes from 'prop-types';
import { FormattedTime } from 'react-intl';

const Time = ({ value }) => (
  <FormattedTime timeZone="Australia/Sydney" value={value}/>
);

Time.propTypes = {
  value: PropTypes.instanceOf(Date).isRequired,
};

export default Time;
