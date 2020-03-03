import { Button, withStyles } from '@material-ui/core';
import { object } from 'prop-types';
import React from 'react';
import { Link } from 'react-router-dom';

const NotFoundPage = ({ classes }) => (
  <div className={classes.root}>
    <h1 className="display-1 medium">Not Found</h1>

    <p>
      Sorry, we can&#39;t find the page you&#39;re looking for.
    </p>

    <div className={classes.buttonContainer}>
      <Link to="/" className={classes.homeLink}>
        <Button variant="contained" color="primary">
          Home
        </Button>
      </Link>
    </div>
  </div>
);

NotFoundPage.propTypes = {
  classes: object.isRequired,
};

const styles = (theme) => ({
  root: {
    margin: theme.spacing(4),
  },
  buttonContainer: {
    marginTop: theme.spacing(4),
  },
  homeLink: {
    textDecoration: 'none',
  },
});

export default withStyles(styles)(NotFoundPage);
