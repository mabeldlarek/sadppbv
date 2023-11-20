import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import MD5 from "crypto-js/md5";


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
  const [mensagem, setMensagem] = useState('');

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };
  useEffect(() => {

    const obterDetalhesUsuario = async () => {
      console.log('ENVIADO: ', headers);
      const metodo = 'GET';
      const caminho = "/usuarios/"+ registro;
      try {
        const response = await fetch("http://" + localStorage.getItem('ip') +":" + localStorage.getItem('porta') + caminho, {
          method: metodo,
          headers: headers
        });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
          const responseData = await response.json();
          console.log('RECEBIDO: ', responseData);
  
          if (response.status === 200) {
            setUsuario(responseData.usuario);
          } else if (response.status === 401) {
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
        setMensagem("Erro ao verificar conta do usuário logado.");
        return null;
      }
    };

    obterDetalhesUsuario();
  }, []); 

  const handleEditarClick = () => {
    setModoEdicao(true);
  };

  const handleExcluirClick = () => {
    deletarUsuario();
  };

  const deletarUsuario = async () => {
    console.log('ENVIADO: ', headers);
    const caminho = "/usuarios/"+ registro;
    try {
      const response = await fetch("http://" + localStorage.getItem('ip') +":" + localStorage.getItem('porta') + caminho, {
        method: 'DELETE',
        headers: headers
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);
        if (response.status === 200) {
          setMensagem(responseData.message);
          navigate('/')
        } else if (response.status === 401) {
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
      setMensagem("Erro ao deletar usuário.");
      return null;
    }
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

  const editar = async () => {
    usuario.senha = MD5(usuario.senha).toString();
    console.log(usuario.senha);
    const corpo = JSON.stringify(usuario);
    console.log('ENVIADO: ', headers, " ", corpo);

    try {
      const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + "/usuarios/" + usuario.registro, {
        method: 'PUT',
        headers: headers,
        body: corpo,
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);

        if (response.status === 200) {
          setMensagem(responseData.message);
        } else if (response.status === 401) {
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
      setMensagem("Erro ao editar usuário.");
    }
  }

  return (
    <Container>
      <h2>Minha Conta</h2>
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
        {modoEdicao ? (
        <Form.Group className="mb-3" controlId="formSenha">
          <Form.Label>Senha</Form.Label>
            <Form.Control
              type="password"
              placeholder="Sua senha"
              value={usuario.senha}
              onChange={(e) => setUsuario({ ...usuario, senha: e.target.value })}
            />
        </Form.Group>) : null}
        <Form.Group className="mb-3" controlId="formRegistro">
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
        <Form.Group className="mb-3" controlId="formTipoUsuario">
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
      <Button variant="secondary" onClick={handleExcluirClick}>
         Excluir
       </Button>
      <div> {mensagem} </div>
    </Container>
  );
};

export default DetalhesConta;