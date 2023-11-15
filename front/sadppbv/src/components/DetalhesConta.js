import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const DetalhesConta = () => {
  const [usuario, setUsuario] = useState({
    email: '',
    nome: '',
    senha: '',
  });

  const navigate = useNavigate();

  const token = localStorage.getItem('token');
  const registro = localStorage.getItem('registro');
  const [modoEdicao, setModoEdicao] = useState(false);

  useEffect(() => {

    const obterDetalhesUsuario = async () => {
      try {
        const response = await fetch("http://" + localStorage.getItem('ip') +":" + localStorage.getItem('porta') +"/usuarios/"+ registro, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-type': 'application/json',
            'Accept': 'application/json',
            'Cache-Control': 'no-cache'
          },
        });
        if (response.ok) {
          const data = await response.json();
          setUsuario(data.usuario); 
        } else {
          console.error(`Erro na solicitação: ${response.status}`);
        }
      } catch (error) {
        console.error('Erro ao obter detalhes do usuário:', error);
      }
    };

    obterDetalhesUsuario();
  }, []); 

  const editar = async () => {
    try {
        const response = await fetch("http://" + localStorage.getItem('ip') +":" + localStorage.getItem('porta') +"/usuarios/"+ usuario.registro, {
          method: 'PUT',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-type': 'application/json',
            'Accept': 'application/json',
            'Cache-Control': 'no-cache'
          },
          body: JSON.stringify(usuario),
        });
  
        if (response.ok) {
          const data = await response.json();
          console.log(data);
        } else {
          console.error(`Erro na solicitação: ${response.status}`);
        }
      } catch (error) {
        console.error('Erro ao salvar edição:', error);
      }
    }

  const handleEditarClick = () => {
    setModoEdicao(true);
  };

  const handleSalvarEdicao = async () => {
    try {
      setModoEdicao(false);
      editar();
    } catch (error) {
      console.error('Erro ao salvar edição:', error);
    }
  };

  function maskPassword(password) {
    return password.replace(/./g, '*');
  }

  return (
    <Container>
      <h2>Detalhes da Conta</h2>
      <Form>
        <Form.Group className="mb-3" controlId="formNome">
          <Form.Label>Nome</Form.Label>
          {modoEdicao ? (
            <Form.Control
              type="text"
              placeholder="Seu Nome"
              value={usuario.nome}
              onChange={(e) => setUsuario({ ...usuario, nome: e.target.value })}
            />
          ) : (
            <Form.Control type="text" plaintext readOnly value={usuario.nome} />
          )}
        </Form.Group>

        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Email</Form.Label>
          {modoEdicao ? (
            <Form.Control
              type="email"
              placeholder="Seu Email"
              value={usuario.email}
              onChange={(e) => setUsuario({ ...usuario, email: e.target.value })}
            />
          ) : (
            <Form.Control type="text" plaintext readOnly value={usuario.email} />
          )}
        </Form.Group>

        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Senha</Form.Label>
          {modoEdicao ? (
            <Form.Control
              type="password"
              placeholder="Sua senha"
              value={usuario.senha}
              onChange={(e) => setUsuario({ ...usuario, senha: e.target.value })}
            />
          ) : (
            <Form.Control type="password" plaintext readOnly value={maskPassword(usuario.senha)} />
          )}
        </Form.Group>
        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Registro</Form.Label>
          {modoEdicao ? (
            <Form.Control
              type="text"
              plaintext              
              value={usuario.registro}
              readOnly
            />
          ) : (
            <Form.Control type="text" plaintext readOnly value={usuario.registro} />
          )}
        </Form.Group>
        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Tipo de Usuário</Form.Label>
          {modoEdicao ? (
            <Form.Control
              plaintext
              value={usuario.tipo_usuario}
              readOnly
            />
          ) : (
            <Form.Control type="text" plaintext readOnly value={usuario.tipo_usuario} />
          )}
        </Form.Group>

      </Form>

      {modoEdicao ? (
        <Button variant="primary" onClick={handleSalvarEdicao}>
          Salvar Edição
        </Button>
      ) : (
        <Button variant="secondary" onClick={handleEditarClick}>
          Editar
        </Button>
      )}
    </Container>
  );
};

export default DetalhesConta;