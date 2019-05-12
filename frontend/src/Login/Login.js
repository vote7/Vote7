import React, { Component } from "react";
import "./Login.css"

export class Login extends Component {

    render() {
        const login = () => {
            this.props.login()
            this.props.history.push("/home/")
        }
        return (
            <div className="login-container">
                Login:
                <button onClick={login}> Login </button>
            </div>
        );
    }
}