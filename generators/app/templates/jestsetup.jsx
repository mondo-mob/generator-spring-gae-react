import { ThemeProvider } from '@material-ui/core/styles';
import Enzyme, { mount, render, shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import React from 'react';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';

Enzyme.configure({ adapter: new Adapter() });

const mockStore = (state) => {
  const getStoreFn = configureStore();
  const store = getStoreFn(state);
  jest.spyOn(store, 'dispatch');
  return store;
};

const component = (node, state = {}) => (
  <Provider store={mockStore(state)}>
    <IntlProvider locale="en-AU">
      <ThemeProvider>
        {node}
      </ThemeProvider>
    </IntlProvider>
  </Provider>
);
// Make Enzyme functions available in all test files without importing
global.shallow = (node, state) => {
  if (state) {
    return shallow(node, { context: { store: mockStore(state) } }).dive();
  }
  return shallow(node);
};
global.render = (node, state) => render(component(node, state));
global.mount = (node, state) => mount(component(node, state));

global.getStore = (cmp) => {
  const provider = cmp.find('Provider');
  if (provider.exists()) {
    return provider.prop('store');
  }

  return cmp.context().store;
};

global.tick = () => new Promise(resolve => setTimeout(resolve));
