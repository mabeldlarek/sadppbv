import Logo from '../../resources/img/logo.png'
import React, { useState, useEffect } from 'react';
import { Outlet, Link } from "react-router-dom";
import { Dropdown, Nav, NavItem, Navbar, Container } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import ListaUsuarios from '../usuarios/ListaUsuarios';
import DetalhesConta from '../usuarios/DetalhesConta';
import ListaPontos from '../pontos/ListaPontos';
import ListaSegmentos from '../segmentos/ListaSegmentos';
import DetalhesRota from '../DetalhesRota';

function NavigationAdm() {
  const navigate = useNavigate();

  const [mensagem, setMensagem] = useState('');
  const ip = localStorage.getItem('ip');
  const porta = localStorage.getItem('porta');
  const token = localStorage.getItem('token');
  const [listaUsuariosVisivel, setListaUsuariosVisivel] = useState(false);
  const [visualizacaoVisivel, setVisualizacaoVisivel] = useState(false);

  const [listaPontosVisivel, setListaPontosVisivel] = useState(false);
  const [listaSegmentosVisivel, setSegmentosPontosVisivel] = useState(false);
  const [visualizacaoRota, setVisualizacaoRota] = useState(false);


  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  const rota = () => {
    setListaUsuariosVisivel(false)
    setVisualizacaoVisivel(false);
    setListaPontosVisivel(false);
    setSegmentosPontosVisivel(false);
    setVisualizacaoRota(true);
  };

  const toggleListaUsuarios = () => {
      setVisualizacaoVisivel(false);
      setListaUsuariosVisivel(!listaUsuariosVisivel);
      setListaPontosVisivel(false);
      setSegmentosPontosVisivel(false);
      setVisualizacaoRota(false);

  };

  const minhaConta = () => {
    setListaUsuariosVisivel(false)
    setVisualizacaoVisivel(true);
    setListaPontosVisivel(false);
    setSegmentosPontosVisivel(false);
    setVisualizacaoRota(false);

};

const segmentos = () => {
  setSegmentosPontosVisivel(true);
  setListaUsuariosVisivel(false)
  setVisualizacaoVisivel(false);
  setListaPontosVisivel(false);
  setVisualizacaoRota(false);

};

const pontos = () => {
  setListaPontosVisivel(true);
  setListaUsuariosVisivel(false)
  setVisualizacaoVisivel(false);
  setSegmentosPontosVisivel(false);
  setVisualizacaoRota(false);
};

  const realizarLogout = async () => {

    console.log('ENVIADO: ', headers);

    try {
        const response = await fetch("http://" + ip + ":" + porta + "/logout", {
            method: 'post',
            headers: headers
        });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
            const responseData = await response.json();

            console.log('RECEBIDO: ', responseData);

            if (response.status === 200) {
              localStorage.removeItem('token');
              navigate('/');
            } else if (response.status === 401) {
                console.error(responseData.message);
                setMensagem(responseData.message);
            }
              else if (response.status === 403) {
              console.error(responseData.message);
              setMensagem(responseData.message);
          }
        } else {
            console.error(`Erro na solicitação: ${response.status}`);
            setMensagem(`Erro na solicitação: ${response.status}`);

            const responseText = await response.text();
            console.log('Resposta completa:', responseText);
        }
    } catch (error) {
        console.error(error);
        setMensagem("Erro ao realizar logout.");
        return null;
    }
}

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
          <Nav.Link href="#" onClick={minhaConta}>Minha Conta</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link href="#" onClick={segmentos}>Segmentos</Nav.Link>
        </Nav.Item>
        <Nav.Item>
          <Nav.Link href="#" onClick={pontos}>Pontos</Nav.Link>
        </Nav.Item>
        <Nav.Item>
        <Dropdown>
            <Dropdown.Toggle variant="light" id="dropdown-basic">
                Usuarios
            </Dropdown.Toggle>
            <Dropdown.Menu>
                <Dropdown.Item className={`custom-link}`} onClick={toggleListaUsuarios}>
                  Listar Usuários
                </Dropdown.Item>
            </Dropdown.Menu>
            
        </Dropdown>
        </Nav.Item>
        <Nav.Item>
            <Nav.Link href="#" onClick={rota}>Calcular rota</Nav.Link>
          </Nav.Item>
      </Nav>
      {mensagem}
        </Container>
      </Navbar>
      {listaUsuariosVisivel && <Dropdown.Item><ListaUsuarios/></Dropdown.Item>}
      {visualizacaoVisivel && <DetalhesConta />}
      {listaPontosVisivel && <ListaPontos />}
      {listaSegmentosVisivel && <ListaSegmentos />}
      {visualizacaoRota && <DetalhesRota />}
    <Outlet />
    </>
  );
}

export default NavigationAdm;



