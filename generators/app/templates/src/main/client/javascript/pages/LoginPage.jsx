import { func, object } from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { SubmissionError } from 'redux-form';
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
    const next = location.query.next || '/';

    return login(values)
      .then(() => navigateTo(next))
      .catch(() => {
        // TODO: Map real error message when we fix up rest api responses
        throw new SubmissionError({ _error: { messages: ['Invalid login'] } });
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
