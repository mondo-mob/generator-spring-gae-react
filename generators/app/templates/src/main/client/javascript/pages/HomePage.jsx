import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import moment from 'moment';
import React from 'react';
import { compose } from 'redux';
import { reduxForm } from 'redux-form';
import TimeAgo from '../components/common/TimeAgo';
import './HomePage.less';

const HomePage = () => (
  <div className="home-page">
    <h1 className="display-2">Hello 3wks!</h1>
    <h2 className="display-1">Some bundled widgets</h2>
    <div className="widgets">
      <div>
        Overwrite this page when you create your app, but in the mean time here is some info ...
      </div>
      <Card>
        <CardHeader
          title="TimeAgo"
        />
        <CardContent>
          <p>
            A widget that displays a date/time as &quot;time ago&quot; and optionally
            click to expand to full date/time and collapse again.
          </p>
          <p>
            <TimeAgo value={moment().subtract(5, 'minutes').toDate()} expandable/>
          </p>
        </CardContent>
      </Card>
    </div>
  </div>
);

const form = reduxForm({ form: 'widget-form' });
export default compose(form)(HomePage);
