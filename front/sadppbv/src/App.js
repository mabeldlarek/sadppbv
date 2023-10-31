import { useEffect, useState } from 'react';
import './App.css';
import Formulario from './components/Formulario';
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from "react-router-dom";
import Login from './pages/Login';
import Navigation from './components/nav/Navigation';
import Home from './pages/Home';
import React from 'react';


function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigation />}>
          <Route index element={<Home />} />
          <Route path="formulario" element={<Formulario />} />
          <Route path="login" element={<Login />} />
        </Route>
      </Routes>
  </Router>
 );
  
}


export default App;
