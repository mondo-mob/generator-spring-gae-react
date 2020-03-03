import moment from 'moment';
import * as numeral from 'numeral';
import { isNumber } from 'lodash';

export const date = (str, defaultVal) => (str && moment(str).format('DD/MM/YYYY')) || defaultVal;
export const currency = (num) => num && numeral(num).format('$0,0.00');
export const prettyInt = (num) => num && numeral(num).format('0,0');
export const percent = (num) => isNumber(num) && `${num}%`;
export const enumSlugify = (str) => str && str.replace(/_/g, '-').toLowerCase();
