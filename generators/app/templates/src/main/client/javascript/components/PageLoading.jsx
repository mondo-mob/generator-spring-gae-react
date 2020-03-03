import React from 'react';
import PropTypes from 'prop-types';
import { CircularProgress, withStyles } from '@material-ui/core';
import { grey } from '@material-ui/core/colors';

const PageLoading = ({ classes }) => (
  <div className={classes.root}>
    <CircularProgress className={classes.progress} />
  </div>
);

PageLoading.propTypes = {
  classes: PropTypes.object.isRequired,
};

const styles = {
  root: {
    marginLeft: '49%',
    marginTop: '200px',
  },
  progress: {
    color: grey[500],
    fontSize: '30px',
  },
};

export default withStyles(styles)(PageLoading);
