/* eslint-disable react/no-array-index-key,react/jsx-indent,react/jsx-closing-tag-location */
import React, { Component } from 'react';
import Alert from 'react-s-alert';
import Avatar from 'react-avatar';
import { SubmissionError } from 'redux-form';
import { array, object } from 'prop-types';
import { connect } from 'react-redux';
import UserProfileForm from '../../components/forms/UserProfileForm';
import { getReferenceDataRoles } from '../../reducers';
import api from '../../services/api';
import LoadingIndicator from '../../components/LoadingIndicator';
import NotFoundPage from '../NotFoundPage';
import './UserProfilePage.less';

class UserProfilePage extends Component {
  static propTypes = {
    match: object.isRequired,
    roles: array.isRequired,
  };

  state = {
    isFetching: true,
    user: null,
  };

  componentDidMount() {
    const { match: { params } } = this.props;
    this.fetchUser(params.userId);
  }

  handleSubmit = values =>
    api.users
      .save(values)
      .then(() => Alert.success('User updated'))
      .catch((error) => {
        Alert.error(<div>
          {error.messages.map((err, index) => <p key={index}>{err}</p>)}
        </div>);
        throw new SubmissionError({ _error: error });
      });

  fetchUser(userId) {
    this.setState({ isFetching: true });

    api.users
      .get(userId)
      .then(user => this.setState({ isFetching: false, user }))
      .catch((error) => {
        this.setState({ isFetching: false });

        if (error.type !== 'NotFoundException') {
          Alert.error(error.message);
        }
      });
  }

  render() {
    const { isFetching, user } = this.state;
    const { roles } = this.props;

    if (isFetching) {
      return <LoadingIndicator size={60}/>;
    }

    if (!isFetching && !user) {
      return <NotFoundPage/>;
    }

    return (
      <div className="user-profile-page">
        <Avatar className="avatar" name={user.name} email={user.email} size={96} round/>

        <UserProfileForm initialValues={user} roles={roles} onSubmit={this.handleSubmit}/>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  roles: getReferenceDataRoles(state).map(input => ({
    value: input.id,
    label: input.description,
  })),
});

export default connect(mapStateToProps)(UserProfilePage);
