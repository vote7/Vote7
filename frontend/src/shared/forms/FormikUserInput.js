import React from "react";
import { FormGroup } from "./FormGroup";
import { ChooseUserInput } from "./ChooseUserInput";

export const FormikUserInput = ({
  field: { name, value, onChange, onBlur },
  form: { touched, errors },
  label,
}) => {
  const error = (touched[name] && errors[name]) || false;

  return (
    <FormGroup label={label} error={error}>
      <ChooseUserInput
        value={value}
        onChange={v => onChange({ target: { name, value: v } })}
        onBlur={() => onBlur({ target: { name } })}
        isInvalid={Boolean(error)}
      />
    </FormGroup>
  );
};
