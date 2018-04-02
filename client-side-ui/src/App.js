import React, { Component } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import FavoritesPage from './components/FavoritesPage';
import {Navbar, NavItem } from 'react-materialize';
import './App.css';

import SearchPage from './components/SearchPage';

class App extends Component {
 
  render() {
    const SearchPageComponent = () => (<SearchPage />);
    const FavoritesPageComponent = () => (<FavoritesPage />);
    return (
      <div>     
        <Navbar brand='Wifi HotSpots' right>
          <NavItem href='/favorites'>Favorites</NavItem>
        </Navbar>
        <Router>
            <Switch>
              <Route exact path="/" render={SearchPageComponent}/>
              <Route exact path="/favorites" render={FavoritesPageComponent}/>
            </Switch>
        </Router>
      </div>

    );
  }
}

export default App;
