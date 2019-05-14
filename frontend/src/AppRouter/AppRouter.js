import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import "./AppRouter.css"
import {home, about, users} from './Mocks'
import {Login} from '../Login/Login'
import {Register} from '../Register/Register'
import history from './history';
import 'materialize-css/dist/css/materialize.min.css'
import M from 'materialize-css/dist/js/materialize.min.js'

class AppRouter extends Component {

    constructor(props) {
        super(props);
        this.state = {}
    }

    render() {
        return (
            <div className="navigation-container">
                <Router history={history}>
                    <div className="main-conainer">
                        
                        <button className="nav-btn">
                            <Link to="/"> Home </Link>
                        </button>
                        <button className="nav-btn">
                            <Link to="/about/"> about </Link>
                        </button>
                        <button className="nav-btn">
                            <Link to="/users/"> users </Link>
                        </button>
                        {this.props.logged === false ? 
                            <button className="nav-btn">
                                <Link to="/login/"> Login </Link>
                            </button>
                            : <h/>
                        }
                        {this.props.logged === false ? 
                            <button className="nav-btn">
                                <Link to="/register/"> Register </Link>
                            </button>
                            : <h/>
                        }
                        
                    </div>
                    <div className="content-container">
                        <Route path="/" exact render={(props) => (
                            this.props.logged === true 
                            ? home
                            : <Login history={history} login={this.props.login}/>
                        )} /> 
                        <Route path="/about/" component={about} />
                        <Route path="/users/" component={users} />
                        <Route path="/login/" render={(props) => <Login login={this.props.login}/>} />
                        <Route path="/register/" render={(props) => <Register />} />
                    </div>
                </Router>
            </div>
        );
    }
}

export default AppRouter;
