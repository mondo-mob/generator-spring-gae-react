import api from '../services/api';
import { asyncAction } from './actions';

export const fetchReferenceData = () => asyncAction(
  'REFERENCE_DATA_FETCH',
  api.referenceData.list(),
);
