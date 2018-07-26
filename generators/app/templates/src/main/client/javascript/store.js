import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import reduxCatch from 'redux-catch';
import createHistory from 'history/createBrowserHistory';
import { connectRouter, routerMiddleware } from 'connected-react-router';
import { composeWithDevTools } from 'redux-devtools-extension';
import rootReducer from './reducers';
import { logReduxError } from './util/errors';

// Create a history of your choosing (we're using a browser history in this case)
export const history = createHistory();

const middleware = [
  routerMiddleware(history),
  thunk,
  reduxCatch(logReduxError),
];

const store = createStore(connectRouter(history)(rootReducer), composeWithDevTools(applyMiddleware(...middleware)));

export default store;
