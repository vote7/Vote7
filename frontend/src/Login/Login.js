import React, { Component } from "react";
import "./Login.css"
import 'materialize-css/dist/css/materialize.min.css'
import M from 'materialize-css/dist/js/materialize.min.js'

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