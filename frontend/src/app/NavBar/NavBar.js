import { Link } from "react-router-dom";
import React, { useContext } from "react";
import { RootContext } from "../RootContext";
import "./NavBar.css";
import logo from "../../logo.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSignOutAlt } from "@fortawesome/free-solid-svg-icons";

const Nav = () => {
  const { isAuthenticated, user, logout } = useContext(RootContext);

  if (!isAuthenticated) return null;

  return (
    <>
      <ul className="navbar-nav">
        <li className="nav-item">
          <Link className="nav-link" to="/polls">
            Polls
          </Link>
        </li>
        <li className="nav-item">
          <Link className="nav-link" to="/groups">
            Groups
          </Link>
        </li>
      </ul>
      <a
        className="ml-auto mr-auto btn btn-outline-primary"
        href="https://drive.google.com/open?id=1K2Y33wpRv_NiVCqbeq_jc1Cl6-p6WI44"
      >
        <strong>Download Android app</strong>
      </a>
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
