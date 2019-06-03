import React, { useContext, useState } from "react";
import { RootContext } from "../../app/RootContext";
import CenteredFormContainer from "../../shared/forms/CenteredFormContainer";
import { GroupApi } from "../../api/GroupApi";
import { Redirect } from "react-router-dom";
import { Field, Form, Formik } from "formik";
import { Button } from "react-bootstrap";
import { FormikTextInput } from "../../shared/forms/formikInputs";

const GroupForm = ({ initial, onSubmit }) => {
  const validate = ({ name }) => {
    const errors = {};
    if (!name) {
      errors.name = "Required";
    }
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
          <Button type="submit" disabled={isSubmitting}>
            Create
          </Button>
        </Form>
      )}
    </Formik>
  );
};

export const NewGroup = () => {
  const { token } = useContext(RootContext);
  const [done, setDone] = useState(false);

  const initial = { name: "", description: "" };

  const onSubmit = ({ name, description }) => {
    GroupApi.create(token, { name, description }).then(() => setDone(true));
  };

  if (done) {
    return <Redirect to="/groups" />;
  }

  return (
    <CenteredFormContainer title="New group">
      <GroupForm initial={initial} onSubmit={onSubmit} />
    </CenteredFormContainer>
  );
};
