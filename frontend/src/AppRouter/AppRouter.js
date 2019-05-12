import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import "./AppRouter.css"
import {home, about, users} from './Mocks'
import {Login} from '../Login/Login'
import history from './history';

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
                    </div>
                </Router>
            </div>
        );
    }
}

export default AppRouter;
