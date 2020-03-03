import { Button, Dialog, DialogActions, DialogContent, makeStyles, Slide } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';
import { useDispatch } from 'react-redux';
import { submit } from 'redux-form';
import cx from 'classnames';
import DialogTitleWithClose from '../common/DialogTitleWithClose';

const Transition = React.forwardRef((props, ref) => <Slide ref={ref} direction="left" {...props} />);

const useStyles = makeStyles((theme) => ({
  allowOverflow: {
    overflowY: 'visible',
  },
  padded: {
    paddingRight: theme.spacing(2),
  },
}));

const FormDialog = ({
  title,
  open,
  cancelButtonText,
  submitButtonText,
  deleteButtonText,
  onCancel,
  onSubmit,
  onDelete,
  formName,
  submitting,
  maxWidth,
  allowOverflow,
  initialValues,
  formComponent: FormComponent,
  ...rest
}) => {
  const dispatch = useDispatch();
  const classes = useStyles();

  const handleSubmit = () => {
    dispatch(submit(formName));
  };

  const handleDelete = () => onDelete && onDelete(initialValues);

  const actions = (
    <>
      <Button variant="text" onClick={onCancel}>
        {cancelButtonText}
      </Button>
      {onDelete && (
        <Button variant="text" secondary onClick={handleDelete}>
          {deleteButtonText}
        </Button>
      )}
      <Button variant="text" color="primary" disabled={submitting} onClick={handleSubmit}>
        {submitButtonText}
      </Button>
    </>
  );

  return (
    <Dialog
      classes={{ paper: allowOverflow ? classes.allowOverflow : '' }}
      title={title}
      open={open}
      onClose={onCancel}
      fullWidth
      maxWidth={maxWidth}
      TransitionComponent={Transition}
    >
      <DialogTitleWithClose title={title} onClose={onCancel} />
      <DialogContent className={cx({ [classes.allowOverflow]: allowOverflow })}>
        <FormComponent onSubmit={onSubmit} {...rest} initialValues={initialValues} />
      </DialogContent>
      <DialogActions className={classes.padded}>{actions}</DialogActions>
    </Dialog>
  );
};

FormDialog.propTypes = {
  title: PropTypes.string.isRequired,
  formComponent: PropTypes.oneOfType([PropTypes.func, PropTypes.object]).isRequired,
  formName: PropTypes.string.isRequired,
  onCancel: PropTypes.func.isRequired,
  onSubmit: PropTypes.func.isRequired,
  onDelete: PropTypes.func,
  open: PropTypes.bool,
  cancelButtonText: PropTypes.string,
  submitButtonText: PropTypes.string,
  deleteButtonText: PropTypes.string,
  maxWidth: PropTypes.string,
  submitting: PropTypes.bool,
  initialValues: PropTypes.object,
  allowOverflow: PropTypes.bool,
};

FormDialog.defaultProps = {
  open: false,
  cancelButtonText: 'Cancel',
  submitButtonText: 'Submit',
  deleteButtonText: 'Delete',
  maxWidth: 'sm',
  onDelete: null,
  initialValues: null,
  submitting: false,
  allowOverflow: false,
};

export default FormDialog;
