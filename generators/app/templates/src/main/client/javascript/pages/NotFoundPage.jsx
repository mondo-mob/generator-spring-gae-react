import Button from '@material-ui/core/Button';
import React from 'react';
import { Link } from 'react-router-dom';

const NotFoundPage = () => (
  <div>
    <h1 className="display-1">Not Found</h1>

    <p>
      Sorry, we can&#39;t find the page you&#39;re looking for.
    </p>

    <Button
      variant="raised"
      containerElement={<Link to="/" />}
    >
      Home
    </Button>
  </div>
);

export default NotFoundPage;
