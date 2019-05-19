import React from "react";

const CenteredFormContainer = ({ children, title }) => (
  <div className="d-flex justify-content-center">
    <div style={{ width: "100%", maxWidth: "400px" }}>
      <h1 className="mt-5 mb-4 text-center">{title}</h1>
      {children}
    </div>
  </div>
);

export default CenteredFormContainer;
