import React, { useContext, useState } from "react";
import Button from "react-bootstrap/Button";
import Api from "../../api/Api";
import { RootContext } from "../../app/RootContext";
import CenteredFormContainer from "../../shared/forms/CenteredFormContainer";
import { Redirect } from "react-router-dom";
import { Field, Form, Formik } from "formik";
import { FormikChooseGroupInput, FormikChooseUserInput, FormikTextInput } from "../../shared/forms/formikInputs";

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
            component={FormikChooseUserInput}
          />
          <Field
            name="chairmanId"
            type="number"
            label="Chairman"
            component={FormikChooseUserInput}
          />
          <Field name="groupId" label="Group" component={FormikChooseGroupInput} />
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
