import { requestJSON } from './http';

const list = () =>
  requestJSON('/reference-data');

export default {
  list,
};
