import React from "react";
import { FormGroup } from "./FormGroup";
import { ChooseGroupInput } from "./ChooseGroupInput";
import { ChooseUserInput } from "./ChooseUserInput";

const TextInput = ({ isInvalid, ...rest }) => (
  <input
    className={"form-control " + (isInvalid ? "is-invalid" : "")}
    {...rest}
  />
);

const asFormikField = Component => {
  return ({
    field: { name, value, onChange, onBlur },
    form: { touched, errors },
    label,
  }) => {
    const error = (touched[name] && errors[name]) || false;

    return (
      <FormGroup label={label} error={error}>
        <Component
          name={name}
          value={value}
          onChange={onChange}
          onBlur={onBlur}
          isInvalid={Boolean(error)}
        />
      </FormGroup>
    );
  };
};

export const FormikTextInput = asFormikField(TextInput);
export const FormikChooseUserInput = asFormikField(ChooseUserInput);
export const FormikChooseGroupInput = asFormikField(ChooseGroupInput);
