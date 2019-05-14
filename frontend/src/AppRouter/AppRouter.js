import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import {home, about, users} from './Mocks'
import {Login} from '../Login/Login'
import {Register} from '../Register/Register'
import history from './history';
import 'materialize-css/dist/css/materialize.min.css'
import "./AppRouter.css"
import M from 'materialize-css/dist/js/materialize.min.js'

class AppRouter extends Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <div class="main-container">
                <Router history={history}>
                    <div class="navigation-container">
                        
                        <button class="nav-btn">
                            <Link to="/"> Home </Link>
                        </button>
                        <button class="nav-btn">
                            <Link to="/about/"> about </Link>
                        </button>
                        <button class="nav-btn">
                            <Link to="/users/"> users </Link>
                        </button>
                        {this.props.logged === false ? 
                            <button class="nav-btn">
                                <Link to="/login/"> Login </Link>
                            </button>
                            : <h/>
                        }
                        {this.props.logged === false ? 
                            <button class="nav-btn">
                                <Link to="/register/"> Register </Link>
                            </button>
                            : <h/>
                        }
                        
                    </div>
                    <div class="content-container">
                        <Route path="/" exact render={(props) => (
                            this.props.logged === true 
                            ? home
                            : <Login history={history} login={this.props.login}/>
                        )} /> 
                        <Route path="/about/" component={about} />
                        <Route path="/users/" component={users} />
                        <Route path="/login/" history={history} login={this.props.login} render={(props) => <Login login={this.props.login}/>} />
                        <Route path="/register/" render={(props) => <Register />} />
                    </div>
                </Router>
            </div>
        );
    }
}

export default AppRouter;
