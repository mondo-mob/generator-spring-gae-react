import moment from 'moment';
import PropTypes from 'prop-types';
import { includes } from 'lodash';
import React, { useEffect, useState } from 'react';
import Moment from 'react-moment';
import './TimeAgo.less';
import DateTime from './DateTime';

const shortLocaleName = 'en-short';
const shortLocaleDefinition = {
  relativeTime: {
    future: 'in %s',
    past: '%s ago',
    s: 'secs',
    m: 'a min',
    mm: '%d mins',
    h: 'an hr',
    hh: '%d hrs',
    d: 'a day',
    dd: '%d days',
    M: 'a mth',
    MM: '%d mths',
    y: 'a yr',
    yy: '%d yrs',
  },
};

const TimeAgo = ({ value, expandable }) => {
  const [showFull, setShowFull] = useState(false);

  useEffect(() => {
    if (!includes(moment.locales(), shortLocaleName)) {
      moment.locale(shortLocaleName, shortLocaleDefinition);
    } else {
      moment.locale(shortLocaleName);
    }
  }, []);

  const toggleShowFull = () => setShowFull(!showFull);

  return (
    <span className="time-ago">
      {expandable && !showFull && <Moment date={value} fromNow onClick={toggleShowFull} style={{ cursor: 'pointer' }} />}
      {expandable && showFull && (
        <span role="button" onClick={toggleShowFull} onKeyPress={toggleShowFull} tabIndex={0} style={{ cursor: 'pointer' }}>
          <DateTime value={value} />
        </span>
      )}
      {!expandable && <Moment date={value} fromNow />}
    </span>
  );
};

TimeAgo.propTypes = {
  value: PropTypes.oneOfType([PropTypes.instanceOf(Date), PropTypes.string]).isRequired,
  expandable: PropTypes.bool,
};

TimeAgo.defaultProps = {
  expandable: false,
};

export default TimeAgo;
