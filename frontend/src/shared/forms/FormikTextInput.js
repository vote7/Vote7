import React from "react";
import { FormGroup } from "./FormGroup";

export const FormikTextInput = ({
  field,
  form: { touched, errors },
  type,
  label,
}) => {
  const error = (touched[field.name] && errors[field.name]) || false;

  return (
    <FormGroup label={label} error={error}>
      <input
        className={"form-control " + (error ? "is-invalid" : "")}
        type={type}
        {...field}
      />
    </FormGroup>
  );
};
