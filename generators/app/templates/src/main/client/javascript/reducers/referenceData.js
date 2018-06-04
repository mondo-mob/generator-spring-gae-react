import { find, get as _get } from 'lodash';

const referenceData = (state = {}, action) => {
  if (action.type === 'REFERENCE_DATA_FETCH_SUCCESS') {
    return action.response;
  }

  return state;
};

export const getAll = (state, type) => state[type] || [];
export const get = (state, type, id) => (state[type] && find(state[type], { id })) || null;
// Get description for the specified id if we can, otherwise just return the id
export const getDescription = (state, type, id) => _get(get(state, type, id), 'description', id);

export default referenceData;
