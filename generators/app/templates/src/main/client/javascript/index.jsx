import React from 'react';
import ReactDOM from 'react-dom';
import 'normalize.css';
import App from './components/App';
import { attachErrorLogger } from './util/errors';

attachErrorLogger();

// Define render as a function so we can re-render when using Hot Module Replacement
const render = (Component) => {
  ReactDOM.render(<Component/>, document.getElementById('root'));
};

// Perform initial render
render(App);

// Wire in Hot Module Replacement
if (module.hot) {
  module.hot.accept('./components/App', () => render(App));
}
