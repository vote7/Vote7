import React, { useContext, useState } from "react";
import Button from "react-bootstrap/Button";
import Api from "../../api/Api";
import { RootContext } from "../../app/RootContext";
import CenteredFormContainer from "../../shared/CenteredFormContainer";
import { Redirect } from "react-router-dom";
import { Field, Form, Formik } from "formik";
import { FormikTextInput } from "../../shared/FormikTextInput";
import { FormikUserInput } from "../../shared/FormikUserInput";
import { FormikGroupInput } from "../../shared/FormikGroupInput";

const NewPollForm = ({ onSubmit }) => {
  const initial = {
    name: "",
    description: "",
    secretaryId: null,
    chairmanId: null,
    groupId: 0,
  };

  const validate = values => {
    const errors = {};
    if (!values.name) errors.name = "Required";
    if (!values.secretaryId) errors.secretaryId = "Required";
    if (!values.chairmanId) errors.chairmanId = "Required";
    if (!values.groupId) errors.groupId = "Required";
    return errors;
  };

  return (
    <Formik initialValues={initial} validate={validate} onSubmit={onSubmit}>
      {({ isSubmitting }) => (
        <Form>
          <Field
            name="name"
            type="text"
            label="Name"
            component={FormikTextInput}
          />
          <Field
            name="description"
            type="text"
            label="Description"
            component={FormikTextInput}
          />
          <Field
            name="secretaryId"
            type="number"
            label="Secretary"
            component={FormikUserInput}
          />
          <Field
            name="chairmanId"
            type="number"
            label="Chairman"
            component={FormikUserInput}
          />
          <Field name="groupId" label="Group" component={FormikGroupInput} />
          <Button
            className="w-100"
            variant="primary"
            type="submit"
            disabled={isSubmitting}
          >
            Create
          </Button>
        </Form>
      )}
    </Formik>
  );
};

export const NewPoll = () => {
  const { token } = useContext(RootContext);
  const [done, setDone] = useState(false);

  const createPoll = poll => {
    Api.createPoll(token, { ...poll, mutable: true }).then(() => setDone(true));
  };

  if (done) return <Redirect to="/polls" />;

  return (
    <CenteredFormContainer title="New Poll">
      <NewPollForm onSubmit={createPoll} />
    </CenteredFormContainer>
  );
};
