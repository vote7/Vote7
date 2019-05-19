import React, { useState, useContext } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Api from "../../Api";
import { Link } from "react-router-dom";
import { RootContext } from "../../RootContext";
import Index from "../../components/CenteredFormContainer";

const LoginForm = ({ login }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const onSubmit = event => {
    event.preventDefault();
    login({ email, password });
  };

  return (
    <Form onSubmit={onSubmit}>
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
        Sign in
      </Button>
      <Link className="btn btn-lg btn-link w-100 mt-4" to="/register">
        Register
      </Link>
    </Form>
  );
};

const LoginPage = () => {
  const { authenticateWithToken } = useContext(RootContext);

  const handleLoginError = error => {
    if (error.code === "BAD_CREDENTIALS") {
      alert("Invalid email or password");
    }
  };

  const login = credentials => {
    Api.login(credentials).then(authenticateWithToken, handleLoginError);
  };

  return (
    <Index title="Sign in">
      <LoginForm login={login} />
    </Index>
  );
};

export default LoginPage;
