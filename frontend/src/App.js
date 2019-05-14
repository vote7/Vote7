import React, { Component } from 'react';
import './App.css';
import AppRouter  from './AppRouter/AppRouter';
import 'materialize-css/dist/css/materialize.min.css'
import M from 'materialize-css/dist/js/materialize.min.js'

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
