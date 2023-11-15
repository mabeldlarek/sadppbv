import Logo from '../../resources/img/logo.png'
import React, { useState, useEffect } from 'react';
import { Outlet, Link } from "react-router-dom";
import { Dropdown, Nav, NavItem, Navbar, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import DetalhesConta from '../DetalhesConta';

function NavigationUser() {

    const navigate = useNavigate();

    const [mensagem, setMensagem] = useState('');
    const ip = localStorage.getItem('ip');
    const porta = localStorage.getItem('porta');
    const token = localStorage.getItem('token');
  
    const [visualizacaoVisivel, setVisualizacaoVisivel] = useState(false);

    const realizarLogout = () => {
      fetch("http://" + ip + ":" + porta + "/logout", {
        method: 'post',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-type': 'application/json',
            'Accept': 'application/json',
            'Cache-Control': 'no-cache'
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

    const minhaConta = () => {
        setVisualizacaoVisivel(true);
    };
  
    return (
      <>
      <Navbar className="custom-navbar">
          <Container>
            <Navbar.Brand href="#home">
              <img
                alt=""
                src={Logo}
                width="200"
                height="100"
                className="d-inline-block align-top"
              />{' '}
            </Navbar.Brand>
            <Nav className="justify-content-end" activeKey="/home">
          <Nav.Item>
            <Nav.Link href="#" onClick={realizarLogout}>Logout</Nav.Link>
          </Nav.Item>
          <Nav.Item>
            <Nav.Link href="#" onClick={minhaConta}>Minha conta</Nav.Link>
          </Nav.Item>
        </Nav>
        {mensagem}
          </Container>
        </Navbar>
            {visualizacaoVisivel && <DetalhesConta />}      <Outlet />
      </>
    );
  }

export default NavigationUser;