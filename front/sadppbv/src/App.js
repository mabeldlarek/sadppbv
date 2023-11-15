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
import NavigationLogged from './components/nav/NavigationLogged';
import NavigationAdm from './components/nav/NavigationAdm';
import { useNavigate } from 'react-router-dom';
import NavigationUser from './components/nav/NavigationUser';
import Redirecionar from './pages/Redirecionar';

function App() {
  const tokenApp = localStorage.getItem('token');

  return (
    <Router>
      <Routes> 
          <Route path="/" element={<Home />} />
          <Route path="formulario" element={<Formulario />}/>
          <Route path="login" element={<Login />} />
          <Route path="adm" element={<NavigationAdm />} />
          <Route path="user" element={<NavigationUser />} />
          <Route path="/adm/:registro" element={<Redirecionar />} />

      </Routes>
  </Router>
 );
  
}


export default App;
