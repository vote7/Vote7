import React, { Component } from "react";
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import "./AppRouter.css"
import {home, about, users} from './Mocks'

class AppRouter extends Component {

    constructor(props) {
        super(props);
        this.logged = props.logged;
        this.state = {}
    }

    render() {
        return (
            <div className="navigation-container">
                <Router>
                    <div clMocks from assName="main-conainer">
                        
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
                            this.logged === true 
                            ? home
                            : <Redirect to='/login'/>
                        )} /> 
                        <Route path="/about/" component={about} />
                        <Route path="/users/" component={users} />
                    </div>
                </Router>
            </div>
        );
    }
}

export default AppRouter;
