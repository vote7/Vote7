import { Link } from "react-router-dom";
import React, { useContext } from "react";
import { RootContext } from "../../RootContext";
import "./NavBar.css";
import logo from "../../logo.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";

const Nav = () => {
  const { isAuthenticated, user, logout } = useContext(RootContext);

  if (!isAuthenticated) return null;

  return (
    <>
      <ul className="navbar-nav mr-auto">
        <li className="nav-item">
          <Link className="nav-link" to="/">
            Home
          </Link>
        </li>
      </ul>
      <ul className="navbar-nav align-items-center">
        <li className="nav-item">
          <strong>
            {user.name} {user.surname}
          </strong>
        </li>
        <li className="nav-item">
          <button className="btn btn-link" onClick={logout}>
            <FontAwesomeIcon icon={faSignOutAlt} />
          </button>
        </li>
      </ul>
    </>
  );
};

const NavBar = () => {
  return (
    <nav className="navbar navbar-expand navbar-light bg-light">
      <Link to="/" className="vote-logo">
        <img src={logo} alt="Vote logo" />
      </Link>
      <div className="collapse navbar-collapse">
        <Nav />
      </div>
    </nav>
  );
};

export default NavBar;
