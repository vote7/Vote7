import React, { Component } from "react";
import "./Login.css"
import 'materialize-css/dist/css/materialize.min.css'
import M from 'materialize-css/dist/js/materialize.min.js'

export class Login extends Component {

    constructor(props) {
        super(props)
        this.state = {login: "", password: ""}
    }

    updateLogin(newLogin) {
        this.setState({login: newLogin})
    }

    updatePassword(newPassword) {
        this.setState({password: newPassword})
    }

    tryLogin() {
        if(this.state.login == "" || this.state.password == "") {
            alert("Login or password is empty")
        } else {
            this.props.login()
            this.props.history.push("/home/")
        }
        
    }

    render() {
        return (
            <div className="login-container">
                <div className="login-text"><h1>Login</h1></div>
                <div className="text-field">
                    Login:
                    <input onChange={(event) => this.updateLogin(event.target.value)} type="text" name="Login"></input>
                </div>
                <div className="text-field">
                    Password:
                    <input onChange={(event) => this.updatePassword(event.target.value)} type="text" name="Password"></input>
                </div>
                <div className="login-btn"><button onClick={() => this.tryLogin()}> Login </button></div>
            </div>
        );
    }
}