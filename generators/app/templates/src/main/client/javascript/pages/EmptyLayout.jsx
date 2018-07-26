import React from 'react';
import PropTypes from 'prop-types';

const EmptyLayout = ({ children }) => (
  <div className="default-layout">
    { children }
  </div>
);

EmptyLayout.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.node,
    PropTypes.arrayOf(PropTypes.node),
  ]).isRequired,
};

export default EmptyLayout;
