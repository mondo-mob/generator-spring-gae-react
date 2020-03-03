import { CircularProgress, withStyles } from '@material-ui/core';
import { string, object } from 'prop-types';
import React from 'react';
import cx from 'classnames';
import './LoadingIndicator.less';

const LoadingIndicator = ({ text, classes, ...rest }) => (
  <div className={cx(['loading-indicator', classes.loadingContainer])}>
    <CircularProgress className="spinner" {...rest} />
    {text && <p className="text">{text}</p>}
  </div>
);

LoadingIndicator.propTypes = {
  text: string,
  classes: object.isRequired,
};

LoadingIndicator.defaultProps = {
  text: null,
};

const styles = {
  loadingContainer: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
};

export default withStyles(styles)(LoadingIndicator);
