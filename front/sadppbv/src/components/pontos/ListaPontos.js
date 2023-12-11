import React, { useState, useEffect } from 'react';
import EdicaoPontos from './EdicaoPontos';
import FormularioPontos from './FormularioPontos';

const ListaPontos = () => {
  const [pontos, setPontos] = useState([]);
  const [modo, setModo] = useState('lista');
  const [pontoEditando, setPontoEditando] = useState(null);
  const [pontoExcluindo, setPontoExcluindo] = useState(null);
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
    console.log('ENVIADO: ', headers);
    const obterPontos = async () => {
      try {
        const response = await fetch(`http://${ip}:${porta}/pontos`, {
          method: 'GET',
          headers: headers
        });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
          const responseData = await response.json();
          console.log('RECEBIDO: ', responseData);
          if (response.status === 200) {
            setPontos(responseData.pontos);
          } else {
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
        setMensagem("Erro ao obter pontos.");
        return null;
      }
    };

    obterPontos();
  }, [pontoEditando, pontoExcluindo]);

  const deletarPonto = async (pontoId) => {
    try {
      const response = await fetch(`http://${ip}:${porta}/pontos/${pontoId}`, {
        method: 'DELETE',
        headers: headers
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();

        if (response.status === 200) {
          setPontoExcluindo(pontoId);
          setModo('lista');
          setMensagem(responseData.message);
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
      setMensagem("Erro ao deletar ponto.");
      return null;
    }
  };

  const cadastrar = () => {
    setModo('cadastro');
  };

  const editarPonto = (ponto) => {
    setModo('edicao');
    setPontoEditando(ponto);
  };

  const salvarCadastro = (pontoCadastrado) => {
    setModo('lista');
  };

  const salvarEdicao = (pontoEditado) => {
    setPontoEditando(pontoEditado);
    setPontoEditando(null);
    setModo('lista');
  };

  return (
    <div>
      {modo === 'lista' && (
        <div>
          <h2>Lista de Pontos</h2>
          <ul className="list-group">
            {pontos.map((ponto) => (
              <li key={ponto.id} className="list-group-item d-flex justify-content-between align-items-center">
                Nome: {ponto.nome} 
                
                <div className="d-flex">
                  <button className="btn btn-primary" onClick={() => editarPonto(ponto)}>
                    Editar
                  </button>
                  <button className="btn btn-danger" onClick={() => deletarPonto(ponto.ponto_id)}>
                    Deletar
                  </button>
                </div>
              </li>
            ))}
          </ul>
          <button className="btn btn-success" onClick={cadastrar}>
            Cadastrar Novo Ponto
          </button>
          <div> {mensagem} </div>
        </div>
      )}

      {modo === 'edicao' && pontoEditando && (
        <EdicaoPontos ponto={pontoEditando} onSaveEdicao={salvarEdicao} />
      )}

      {modo === 'cadastro' && (
        <FormularioPontos onSaveCadastro={salvarCadastro} />
      )}
    </div>
  );
};

export default ListaPontos;
  
