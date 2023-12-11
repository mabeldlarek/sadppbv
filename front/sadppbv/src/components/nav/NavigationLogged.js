import Logo from '../../resources/img/logo.png'
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Outlet, Link } from "react-router-dom";
import DetalhesRota from '../DetalhesRota';


function NavigationLogged() {
  const navigate = useNavigate();

  const [mensagem, setMensagem] = useState('');
  const ip = localStorage.getItem('ip');
  const porta = localStorage.getItem('porta');

  const realizarLogout = () => {
    fetch("http://" + ip + ":" + porta + "/logout", {
      method: 'post',
      body: JSON.stringify(),
      headers: {
        'Authorization': `Bearer ${localStorage.getItem("token")}`,
        'Content-type': 'application/json',
        'Accept': 'application/json'
      }
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.error(`Erro na requisição. Status: ${response.status}`);
          throw new Error('Erro na requisição');
        }
      })
      .then(retorno_convertido => {
        console.log(retorno_convertido);
        if (retorno_convertido.sucess) {
          localStorage.removeItem('token');
          const eventoLogout= new Event('logout');
          window.dispatchEvent(eventoLogout);
          navigate('/');
        } else
          setMensagem(retorno_convertido.message);
      })
      .catch(error => {
        console.error(error);
      });


  }

  return (
    <>
    <nav className="navbar navbar-expand-lg custom-navbar">
      <div className="container-fluid">
        <a className="navbar-brand" href="#">
          <img src={Logo} alt="" width="200" height="100" class="d-inline-block align-text-top" />
        </a>
        <a className="navbar-brand" href="#"></a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
          <div className="navbar-nav">
            <a className="nav-link custom-link " aria-current="page" href="#" onClick={realizarLogout}>Logout</a>
          </div>
          <div className="navbar-nav">
            <Link to="/formulario" className="nav-link custom-link">Cadastro</Link>
          </div>
        </div>
        <div className="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
          
        </div>
      </div>
    </nav>
    <Outlet/>
    </>
  );


}

export default NavigationLogged;
