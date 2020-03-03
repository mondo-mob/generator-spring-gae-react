import moment from 'moment';
import PropTypes from 'prop-types';
import React from 'react';
import { FormattedDate } from 'react-intl';
import Time from './Time';
import './TimeAgo.less';
import { makeStyles } from '@material-ui/core';

const useStyles = makeStyles(() => ({
  timeComponent: {
    fontSize: '90%',
  },
}));

const DateTime = ({ value }) => {
  const classes = useStyles();
  const valueDate = moment(value).toDate();

  return (
    <>
      <FormattedDate value={valueDate} />{' '}
      <span className={classes.timeComponent}>
        <Time value={valueDate} />
      </span>
    </>
  );
};

DateTime.propTypes = {
  value: PropTypes.oneOfType([PropTypes.instanceOf(Date), PropTypes.string]).isRequired,
};

export default DateTime;
