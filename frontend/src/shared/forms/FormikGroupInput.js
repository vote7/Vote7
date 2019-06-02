import React from "react";
import { FormGroup } from "./FormGroup";
import { ChooseGroupInput } from "./ChooseGroupInput";

export const FormikGroupInput = ({
  field: { name, value, onChange, onBlur },
  form: { touched, errors },
  label,
}) => {
  const error = (touched[name] && errors[name]) || false;

  return (
    <FormGroup label={label} error={error}>
      <ChooseGroupInput
        value={value}
        onChange={v => onChange({ target: { name, value: v } })}
        onBlur={() => onBlur({ target: { name } })}
        isInvalid={Boolean(error)}
      />
    </FormGroup>
  );
};
