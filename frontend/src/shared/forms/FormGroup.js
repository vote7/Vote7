import React from "react";

export const FormGroup = ({ label, error, children }) => (
  <div className="form-group">
    <label>{label}</label>
    {children}
    {error && <div className="invalid-feedback d-block">{error}</div>}
  </div>
);
