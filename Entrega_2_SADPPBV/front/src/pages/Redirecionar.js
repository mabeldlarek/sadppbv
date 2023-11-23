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
  const navigate = useNavigate();


  useEffect(() => {
    const verificarUsuario = async () => {
      try {
        const tipoUser = await verificarTipoUser(registro, setMensagem);
        if (tipoUser !== null) {
          setTipoUsuario(tipoUser);
          if(tipoUser === 1){
            redirecionarParaAdm();
          } else{
            redirecionarParaUser();
          }

        }
      } catch (error) {
        console.error(error);
      }
    };

    verificarUsuario();
  }, [setTipoUsuario]);

  const redirecionarParaAdm = () => {
    navigate("/adm");
  };

  const redirecionarParaUser = () => {
    navigate("/user");
  };

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  const verificarTipoUser = async (registro, setMensagem) => {
    console.log('ENVIADO: ', headers,);
    try {
      const response = await fetch(`http://${ip}:${porta}/usuarios/${registro}`, {
        method: 'GET',
        headers: headers
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);
        if (response.status === 200) {
          setMensagem(responseData.message);
          return responseData.usuario.tipo_usuario;
        } else if (responseData.status === 401) {
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
      setMensagem("Erro ao verificar usuário.");
    }
  };

  /*return (
    <div>
      {tipoUsuario === 1 ? (
        <div>
          {redirecionarParaAdm()}
        </div>
      ) : tipoUsuario === 0 ? (
        <div>
          {redirecionarParaUser()}
        </div>
      ) : (
        <div>
          <h1>Carregando...</h1>
          <p>{tipoUsuario}</p>
        </div>
      )}
      {mensagem && <p>{mensagem}</p>}
    </div>
  );*/
};

export default Redirecionar;