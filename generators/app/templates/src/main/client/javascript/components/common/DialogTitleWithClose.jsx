import { DialogTitle, IconButton, makeStyles } from '@material-ui/core';
import React from 'react';
import CloseIcon from '@material-ui/icons/Close';
import { grey } from '@material-ui/core/colors';
import * as PropTypes from 'prop-types';

const useStyles = makeStyles((theme) => ({
  closeButton: {
    position: 'absolute',
    right: theme.spacing(1),
    top: theme.spacing(1),
    color: grey[500],
  },
}));

const DialogTitleWithClose = ({ title, onClose }) => {
  const classes = useStyles();

  return (
    <DialogTitle>
      {title}
      <IconButton aria-label="close" className={classes.closeButton} onClick={onClose}>
        <CloseIcon />
      </IconButton>
    </DialogTitle>
  );
};

DialogTitleWithClose.propTypes = {
  title: PropTypes.string.isRequired,
  onClose: PropTypes.func.isRequired,
};

export default DialogTitleWithClose;
