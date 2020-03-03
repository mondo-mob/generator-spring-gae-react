import { grey, red } from '@material-ui/core/colors';
import { makeStyles } from '@material-ui/core';
import cx from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';
import { MultipleSelect } from 'react-select-material-ui';
import { forOwn } from 'lodash';

const useStyles = makeStyles((theme) => ({
  root: {
    marginBottom: theme.spacing(),
    '& .MuiFormControl-root': {
      zIndex: 'unset', // See issue: https://github.com/iulian-radu-at/react-select-material-ui/issues/28
    },
  },
  error: {
    color: red[500],
    marginTop: 5,
  },
}));

const reactSelectStyles = {
  control: () => ({
    borderBottomColor: '#0000006b',
  }),
  clearIndicator: (base) => ({
    ...base,
    color: grey[600],
    '&:hover': {
      color: grey[900],
    },
  }),
  noOptionsMessage: (base) => ({
    ...base,
    color: grey[600],
    textAlign: 'center',
    padding: 16,
  }),
};

const mixStyleFunction = (reactStyleFn, customStyleFn) => (base, state) => ({
  ...(reactStyleFn ? reactStyleFn(base, state) : {}),
  ...(customStyleFn ? customStyleFn(base, state) : {}),
});

// This just joins the results of optional styles supplied with our pre-defined styles. It's just a bit harder to follow because
// the entries in the styles object are functions. See https://react-select.com/styles
const mixStyles = (customStyles) => {
  const mixed = { ...customStyles };
  forOwn(reactSelectStyles, (value, key) => {
    mixed[key] = mixStyleFunction(value, customStyles[key]);
  });
  return mixed;
};

const MultiSelect = ({ meta, input, customStyles, options, onChange, selectProps, ...rest }) => {
  const classes = useStyles();

  const handleChange = (val) => {
    input.onChange(val);
    onChange(val);
  };

  const mixedStyles = mixStyles(customStyles);

  return (
    <div className={cx(classes.root, { 'form-error': meta.touched && meta.invalid })}>
      <MultipleSelect
        values={input.value}
        styles={mixedStyles}
        onChange={handleChange}
        {...rest}
        options={options}
        SelectProps={{
          styles: mixedStyles,
          msgNoOptionsMatchFilter: 'No options',
          ...selectProps,
        }}
      />

      {meta.touched && meta.invalid && meta.error && <div className={classes.error}>{meta.error}</div>}
    </div>
  );
};

MultiSelect.propTypes = {
  input: PropTypes.object.isRequired,
  meta: PropTypes.object.isRequired,
  options: PropTypes.array.isRequired,
  customStyles: PropTypes.object,
  onChange: PropTypes.func,
  selectProps: PropTypes.object,
};

MultiSelect.defaultProps = {
  onChange: () => {},
  customStyles: {},
  selectProps: {},
};

export default MultiSelect;
