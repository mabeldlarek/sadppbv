import React, { useState, useEffect } from 'react';
import Formulario from './Formulario';
import EdicaoUsuario from './EdicaoUsuario';

const ListaUsuarios = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [modo, setModo] = useState('lista');
  const [usuarioEditando, setUsuarioEditando] = useState(null);
  const [usuarioExcluindo, setUsuarioExcluindo] = useState(null);
  const ip = localStorage.getItem('ip');
  const porta = localStorage.getItem('porta');
  const token = localStorage.getItem('token');
  const [mensagem, setMensagem] = useState('');

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  useEffect(() => {
    const obterUsuarios = async () => {
      console.log('ENVIADO: ', headers);
      try {
        const response = await fetch(`http://${ip}:${porta}/usuarios`, {
          method: 'GET',
          headers: headers
        });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
          const responseData = await response.json();
          console.log('RECEBIDO: ', responseData);

          if (response.status === 200) {
            setUsuarios(responseData.usuarios);
          } else  {
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
        setMensagem("Erro ao obter usuários.");
        return null;
      }
    };

    obterUsuarios();
  }, [usuarioEditando, usuarioExcluindo]);

  const deletarUsuario = async (usuarioRegistro) => {
    console.log('ENVIADO: ', headers);
    try {
      const response = await fetch(`http://${ip}:${porta}/usuarios/${usuarioRegistro}`, {
        method: 'DELETE',
        headers: headers
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);

        if (response.status === 200) {
          setUsuarioExcluindo(usuarioRegistro);
          setModo('lista');
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
      setMensagem("Erro ao deletar usuário.");
      return null;
    }
  };

  const cadastrar = () => {
    setModo('cadastro');
  };

  const editarUsuario = (usuario) => {
    setModo('edicao');
    setUsuarioEditando(usuario);
  };

  const salvarCadastro = (usuarioCadastrado) => {
    setModo('lista');
  };

  const salvarEdicao = (usuarioEditado) => {
    setUsuarioEditando(usuarioEditado)
    setUsuarioEditando(null);
  };

  return (
    <div>
      {modo === 'lista' && (
        <div>
          <h2>Lista de Usuários</h2>
          <ul className="list-group">
            {usuarios.map((usuario) => (
              <li key={usuario.registro} className="list-group-item d-flex justify-content-between align-items-center">
                {usuario.nome} - {usuario.email}
                <div className="d-flex">
                  <button className="btn btn-primary" onClick={() => editarUsuario(usuario)}>
                    Editar
                  </button>
                  <button className="btn btn-danger" onClick={() => deletarUsuario(usuario.registro)}>
                    Deletar
                  </button>
                </div>
              </li>
            ))}
          </ul>
          <button className="btn btn-success" onClick={cadastrar}>
            Cadastrar Novo Usuário
          </button>
          <div> {mensagem} </div>
        </div>
      )}

      {modo === 'edicao' && usuarioEditando && (
        <EdicaoUsuario usuario={usuarioEditando} onSaveEdicao={salvarEdicao} />
      )}

      {modo === 'cadastro' && (
        <Formulario onSaveCadastro={salvarCadastro} />
      )}
    </div>
  );
};

export default ListaUsuarios;