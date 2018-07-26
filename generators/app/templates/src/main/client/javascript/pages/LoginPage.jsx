import { func, object } from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { SubmissionError } from 'redux-form';
import queryString from 'query-string';

import * as authActions from '../actions/auth';
import LoginForm from '../components/forms/LoginForm';
import CenteredPanelLayout from './CenteredPanelLayout';
import './LoginPage.less';

class LoginPage extends Component {
  static propTypes = {
    location: object.isRequired,
    login: func.isRequired,
    navigateTo: func.isRequired,
  };

  handleSubmit = (values) => {
    const { location, login, navigateTo } = this.props;
    const query = queryString.parse(location.search);
    const next = query.next || '/admin';

    return login(values)
      .then(() => navigateTo(next))
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });
  };

  render() {
    return (
      <CenteredPanelLayout title="Sign in">
        <LoginForm onSubmit={this.handleSubmit}/>
      </CenteredPanelLayout>
    );
  }
}


const actions = { ...authActions, navigateTo: push };
export default connect(null, actions)(LoginPage);
