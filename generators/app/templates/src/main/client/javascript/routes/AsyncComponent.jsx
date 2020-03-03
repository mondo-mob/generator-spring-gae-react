import PropTypes from 'prop-types';
import React from 'react';
import applyEachSeries from 'async/applyEachSeries';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import { push } from 'connected-react-router';
import PageLoading from '../components/PageLoading';

class AsyncComponent extends React.Component {
  state = {
    loading: false,
    asyncCompleted: false,
  };

  componentDidMount() {
    const { asyncActions, location } = this.props;

    this.mounted = true;
    this.setState({ loading: true });
    const appliedFunction = applyEachSeries(asyncActions, push, location);
    appliedFunction((error) => {
      if (error) {
        console.error('hook error:', error);
      } // eslint-disable-line no-console

      if (this.mounted) {
        this.setState({
          loading: false,
          asyncCompleted: true,
        });
      }
    });
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  render() {
    const { children } = this.props;
    const { loading, asyncCompleted } = this.state;

    if (loading) {
      return <PageLoading />;
    }

    if (asyncCompleted) {
      return children;
    }
    return null;
  }
}

AsyncComponent.propTypes = {
  location: PropTypes.object.isRequired,
  asyncActions: PropTypes.array,
  children: PropTypes.oneOfType([PropTypes.node, PropTypes.arrayOf(PropTypes.node)]).isRequired,
};

AsyncComponent.defaultProps = {
  asyncActions: [],
};

export default connect()(withRouter(AsyncComponent));
