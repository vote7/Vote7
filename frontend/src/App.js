import React, { Component } from 'react';
import './App.css';
import AppRouter  from './AppRouter';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <AppRouter />
          <h>VOTE 7</h>
        </header>
      </div>
    );
  }
}

export default App;
