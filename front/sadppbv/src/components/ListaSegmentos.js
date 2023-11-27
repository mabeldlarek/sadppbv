import React, { useState, useEffect } from 'react';
import EdicaoSegmentos from './segmentos/EdicaoSegmentos';
import FormularioSegmentos from './segmentos/FormularioSegmentos';

const ListaSegmentos = () => {
    const [segmentos, setSegmentos] = useState([]);
    const [modo, setModo] = useState('lista');
    const [segmentoEditando, setSegmentoEditando] = useState(null);
    const [segmentoExcluindo, setSegmentoExcluindo] = useState(null);
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
      const obterSegmentos = async () => {
        try {
          const response = await fetch(`http://${ip}:${porta}/segmentos`, {
            method: 'GET',
            headers: headers
          });
  
          if (response.status === 200 || response.status === 401 || response.status === 403) {
            const responseData = await response.json();
            console.log('RECEBIDO: ', responseData);
            if (response.status === 200) {
              setSegmentos(responseData.segmentos);
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
          setMensagem("Erro ao obter segmentos.");
          return null;
        }
      };
  
      obterSegmentos();
    }, [segmentoEditando, segmentoExcluindo]);
  
    const deletarSegmento = async (segmentoId) => {
      try {
        const response = await fetch(`http://${ip}:${porta}/segmentos/${segmentoId}`, {
          method: 'DELETE',
          headers: headers
        });
  
        if (response.status === 200 || response.status === 401 || response.status === 403) {
          const responseData = await response.json();
  
          if (response.status === 200) {
            setSegmentoExcluindo(segmentoId);
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
        setMensagem("Erro ao deletar segmento.");
        return null;
      }
    };
  
    const cadastrar = () => {
      setModo('cadastro');
    };
  
    const editarSegmento = (segmento) => {
      setModo('edicao');
      setSegmentoEditando(segmento);
    };
  
    const salvarCadastro = (segmentoCadastrado) => {
      setModo('lista');
    };
  
    const salvarEdicao = (segmentoEditado) => {
      setSegmentoEditando(segmentoEditado);
      setSegmentoEditando(null);
    };
  
    return (
      <div>
        {modo === 'lista' && (
          <div>
            <h2>Lista de Segmentos</h2>
            <ul className="list-group">
              {segmentos.map((segmento) => (
                <li key={segmento.id} className="list-group-item d-flex justify-content-between align-items-center">
                  Ponto Inicial: {segmento.ponto_inicial} - Ponto Final :{segmento.ponto_final}
                  <div className="d-flex">
                    <button className="btn btn-primary" onClick={() => editarSegmento(segmento)}>
                      Editar
                    </button>
                    <button className="btn btn-danger" onClick={() => deletarSegmento(segmento.segmento_id)}>
                      Deletar
                    </button>
                  </div>
                </li>
              ))}
            </ul>
            <button className="btn btn-success" onClick={cadastrar}>
              Cadastrar Novo Segmento
            </button>
            <div> {mensagem} </div>
          </div>
        )}
  
        {modo === 'edicao' && segmentoEditando && (
          <EdicaoSegmentos segmento={segmentoEditando} onSaveEdicao={salvarEdicao} />
        )}
  
        {modo === 'cadastro' && (
          <FormularioSegmentos onSaveCadastro={salvarCadastro} />
        )}
      </div>
    );
  };
  
  export default ListaSegmentos;