import Logo from '../../resources/img/logo.png'
import React, { useState, useEffect } from 'react';
import { Outlet, Link } from "react-router-dom";

function Navigation() {

  const [valoresDefinidos, setValoresDefinidos] = useState(false);

  useEffect(() => {
    const ip = localStorage.getItem('ip');
    const porta = localStorage.getItem('porta');
    if (ip && porta) {
      console.log(ip + porta);
      setValoresDefinidos(true);
    }
  
    const atualizacaoConexaoHandler = () => {
      setValoresDefinidos(true);
    };
  
    window.addEventListener('atualizacaoConexao', atualizacaoConexaoHandler);
  
    return () => {
      window.removeEventListener('atualizacaoConexao', atualizacaoConexaoHandler);
    };
  }, []);

  return (
    <>
    <nav className="navbar navbar-expand-lg custom-navbar">
    <div className="container-fluid">
    <a className="navbar-brand" href="#">
      <img src={Logo} alt="" width="200" height="100" class="d-inline-block align-text-top"/>
    </a>
        <a className="navbar-brand" href="#"></a>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
          <div className="navbar-nav">
          <Link to="/login" className={`nav-link custom-link ${!valoresDefinidos && 'disabled'}`}>Login</Link> 
          <Link to="/formulario" className={`nav-link custom-link ${!valoresDefinidos && 'disabled'}`}>Cadastro</Link>
          </div>
        </div>
    </div>
    </nav>
    <Outlet />
    </>
  );
}

export default Navigation;



