import React, { useEffect, useState } from 'react';
import { redirect, useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import NavigationAdm from '../components/nav/NavigationAdm';
import DetalhesConta from '../components/DetalhesConta';
import NavigationUser from '../components/nav/NavigationUser';

const Redirecionar = () => {
  const [tipoUsuario, setTipoUsuario] = useState(null);
  const [mensagem, setMensagem] = useState('');
  const ip = localStorage.getItem('ip');
  const porta = localStorage.getItem('porta');
  const { registro } = useParams();
  const token = localStorage.getItem('token');


  useEffect(() => {
    const verificarUsuario = async () => {
      try {
        const tipoUsuario = await verificarTipoUser(registro, setMensagem);
        if (tipoUsuario !== null) {
          setTipoUsuario(tipoUsuario);
        }
      } catch (error) {
        console.error(error);
      }
    };
  
    verificarUsuario();
  }, [registro]);
  
  const verificarTipoUser = async (registro, setMensagem) => {
    try {
      const response = await fetch(`http://${ip}:${porta}/usuarios/${registro}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-type': 'application/json',
          'Accept': 'application/json',
          'Cache-Control': 'no-cache'
        }
      });
  
      if (!response.ok) {
        console.error(`Erro na solicitação: ${response.status}`);
        setMensagem(response.message);
        return null; 
      }
  
      const responseData = await response.json();
      console.log(responseData);
  
      if (responseData.success) {
        setTipoUsuario(responseData.usuario.tipo_usuario);
        return responseData.usuario.tipo_usuario;
      } else {
        setMensagem(responseData.message);
        return null; 
      }
    } catch (error) {
      console.error(error);
      setMensagem("Erro ao verificar usuário.");
      return null; 
    }
  };

  return (
    <div>
      {tipoUsuario === 1 ? (
        <div>
          <NavigationAdm/>
        </div>
      ) : tipoUsuario === 0 ? (
        <div>
          <NavigationUser/>
        </div>
      ) : (
        <div>
          <h1>Carregando...</h1>
        </div>
      )}
      {mensagem && <p>{mensagem}</p>}
    </div>
  );
};

export default Redirecionar;