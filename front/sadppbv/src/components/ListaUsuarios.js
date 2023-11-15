import React, { useState, useEffect } from 'react';
import Formulario from './Formulario';
import EdicaoUsuario from './EdicaoUsuario';

const ListaUsuarios = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [modo, setModo] = useState('lista');
  const [modoEdicao, setModoEdicao] = useState(false);
  const [modoCadastro, setModoCadastro] = useState(false);
  const [usuarioCadstrado, setUsuarioCadastrado] = useState(false);
  const [usuarioEditando, setUsuarioEditando] = useState(null);
  const [usuarioExcluindo, setUsuarioExcluindo] = useState(null);
  const ip = localStorage.getItem('ip');
  const porta = localStorage.getItem('porta');
  const token = localStorage.getItem('token');

  useEffect(() => {
    const obterUsuarios = async () => {
      try {
        const response = await fetch(`http://${ip}:${porta}/usuarios`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-type': 'application/json',
            'Accept': 'application/json',
            'Cache-Control': 'no-cache'
          }
        });
        if (response.ok) {
          const data = await response.json();
          setUsuarios(data.usuarios);
        } else {
          console.error(`Erro na solicitação: ${response.status}`);
        }
      } catch (error) {
        console.error('Erro ao obter usuários:', error);
      }
    };

    obterUsuarios();
  }, [usuarioEditando, usuarioExcluindo]);

  const deletarUsuario = async (usuarioRegistro) => {
    try {
      const response = await fetch(`http://${ip}:${porta}/usuarios/${usuarioRegistro}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-type': 'application/json',
          'Accept': 'application/json',
          'Cache-Control': 'no-cache'
        },
      });

      if (response.ok) {
        setUsuarioExcluindo(usuarioRegistro);
        setModo('lista');
      } else {
        console.error(`Erro na solicitação: ${response.status}`);
      }
    } catch (error) {
      console.error('Erro ao deletar usuário:', error);
    }
  };

  const cadastrar = () => {
    setUsuarioCadastrado(false)
    setModo('cadastro');
  };
  
  const editarUsuario = (usuario) => {
    setModo('edicao');
    setModoEdicao(true);
    setUsuarioEditando(usuario);
  };

  const salvarCadastro = (usuarioCadastrado) => {
    setUsuarioCadastrado(true)
    setModo('lista');
  };

  const salvarEdicao = (usuarioEditado) => {
    setUsuarioEditando(usuarioEditado)
    setModoEdicao(false);
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
        </div>
      )}

      {modo === 'edicao' && usuarioEditando && (
        <EdicaoUsuario usuario={usuarioEditando} onSaveEdicao={salvarEdicao} />
      )}

      {modo === 'cadastro' && (
        <Formulario onSaveCadastro={salvarCadastro}/>
      )}
    </div>
  );
};

export default ListaUsuarios;