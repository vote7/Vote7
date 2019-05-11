import React, { Component } from "react";
import "./Login.css"

export class Login extends Component {

    render() {
        return (
            <div className="login-container">
                Login:
                <button onClick={this.props.login()}> Login </button>
            </div>
        );
    }
}