import React, { Component } from 'react';
import './App.css';
import "./AppRouter.css"
import AppRouter  from './AppRouter';

class App extends Component {
  constructor(props) {
    super(props)
    this.state = {logged: false}
  }

  render() {
    return (
      <div>
        <AppRouter logged={this.state.logged} login={() => {this.setState({logged: true})}}/>
      </div>
    );
  }
}

export default App;
