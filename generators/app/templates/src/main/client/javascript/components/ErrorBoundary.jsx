import React, { Component } from 'react';
import * as PropTypes from 'prop-types';
import { logComponentError } from '../util/errors';

class ErrorBoundary extends Component {
  static propTypes = {
    children: PropTypes.node.isRequired,
    renderError: PropTypes.oneOf([PropTypes.func, PropTypes.object]),
  };

  static defaultProps = {
    renderError: undefined,
  };

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentDidCatch(error, { componentStack }) {
    logComponentError(error, componentStack);
    console.error(componentStack, error);

    this.setState({ hasError: true, error });
  }

  componentDidUpdate(prevProps) {
    if (prevProps.children !== this.props.children) {
      // eslint-disable-next-line react/no-did-update-set-state
      this.setState({
        hasError: false,
      });
    }
  }

  render() {
    const { hasError, error, info } = this.state;
    const { children, renderError: Error } = this.props;

    if (hasError) {
      if (Error) {
        return <Error error={error} info={info} />;
      }
      return <div>An error has occurred and been logged.</div>;
    }

    return children;
  }
}

export default ErrorBoundary;
