import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { SubmissionError } from 'redux-form';
import { func, object } from 'prop-types';
import { intersection } from 'lodash';
import CenteredPanelLayout from './CenteredPanelLayout';
import RegisterForm from '../components/forms/RegisterForm';
import * as authActions from '../actions/auth';

const isAdmin = user =>
  user && intersection(['super', 'admin'], user.roles).length > 0;

const navigate = (navigateTo, user) => navigateTo(isAdmin(user) ? '/admin' : '/');

class RegisterPage extends Component {
  static propTypes = {
    navigateTo: func.isRequired,
    match: object.isRequired,
    register: func.isRequired,
    fetchUser: func.isRequired,
  };

  componentDidMount() {
    const { fetchUser, navigateTo } = this.props;
    fetchUser().then((user) => {
      if (user) {
        navigate(navigateTo, user);
      }
    });
  }

  handleSubmit = (values) => {
    const { match: { params }, register, navigateTo } = this.props;

    return register(params.inviteCode, values)
      .then((user) => {
        navigate(navigateTo, user);
      })
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });
  };

  render() {
    return (
      <CenteredPanelLayout title="Setup account">
        <p className="lead">
          To complete your account setup please<br/> tell us your name and choose a password
        </p>
        <RegisterForm onSubmit={this.handleSubmit} />
      </CenteredPanelLayout>
    );
  }
}

const actions = { ...authActions, navigateTo: push };

export default connect(null, actions)(RegisterPage);
