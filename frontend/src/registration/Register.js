import React, { useContext, useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Api from "../api/Api";
import { Link } from "react-router-dom";
import { RootContext } from "../app/RootContext";
import CenteredFormContainer from "../shared/forms/CenteredFormContainer";

const RegisterForm = ({ register }) => {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const onSubmit = event => {
    event.preventDefault();
    const [name = "", surname = ""] = fullName.split(" ", 2);
    register({ name, surname, email, password });
  };

  return (
    <Form onSubmit={onSubmit}>
      <Form.Group>
        <Form.Control
          type="text"
          required
          placeholder="Name"
          size="lg"
          value={fullName}
          onChange={event => setFullName(event.target.value)}
        />
      </Form.Group>
      <Form.Group>
        <Form.Control
          type="email"
          required
          placeholder="Email"
          size="lg"
          value={email}
          onChange={event => setEmail(event.target.value)}
        />
      </Form.Group>
      <Form.Group>
        <Form.Control
          type="password"
          required
          placeholder="Password"
          size="lg"
          value={password}
          onChange={event => setPassword(event.target.value)}
        />
      </Form.Group>
      <Button className="w-100" size="lg" variant="primary" type="submit">
        Register
      </Button>
      <Link className="btn btn-lg btn-link w-100 mt-4" to="/login">
        Sign in
      </Link>
    </Form>
  );
};

const Register = () => {
  const { authenticateWithToken } = useContext(RootContext);

  const handleRegisterError = error => {
    if (error.code === "USER_ALREADY_REGISTERED") {
      alert("An account with this email already exist");
    }
  };

  const register = user => {
    Api.register(user).then(authenticateWithToken, handleRegisterError);
  };

  return (
    <CenteredFormContainer title="Register">
      <RegisterForm register={register} />
    </CenteredFormContainer>
  );
};

export default Register;
