import { applyMiddleware, createStore } from 'redux';
import thunk from 'redux-thunk';
import reduxCatch from 'redux-catch';
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import { composeWithDevTools } from 'redux-devtools-extension';
import rootReducer from './reducers';
import { logReduxError } from './util/errors';

// Create a history of your choosing (we're using a browser history in this case)
export const history = createBrowserHistory();

const middleware = [routerMiddleware(history), thunk, reduxCatch(logReduxError)];

const store = createStore(rootReducer(history), composeWithDevTools(applyMiddleware(...middleware)));

export default store;
