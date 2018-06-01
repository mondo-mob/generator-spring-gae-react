import { withStyles } from '@material-ui/core';
import Chip from '@material-ui/core/Chip';
import * as React from 'react';

import MaterialSelectField from './MaterialSelectField';

const styles = {
  root: {
    '& .Select-input': {
      height: 32,
    },
  },
};

// eslint-disable-next-line react/prefer-stateless-function
class ChipSelectField extends React.Component {
  render() {
    return (<MaterialSelectField
      {...this.props}
      valueComponent={({ value, onRemove }) => (
        <Chip
          key={value.value}
          label={value.label}
          onDelete={() => {
            onRemove(value);
          }}
        />)}
    />);
  }
}

ChipSelectField.propTypes = MaterialSelectField.propTypes;

ChipSelectField.defaultProps = MaterialSelectField.defaultProps;

export default withStyles(styles)(ChipSelectField);
