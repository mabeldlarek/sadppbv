import React, { useState } from 'react';

const FormularioSegmentos = ({ segmentoParaEdicao }) => {
  const segmentoInicial = {
    distancia: 0.0,
    ponto_inicial: 0,
    ponto_final: 0,
    status: 0,
    direcao: '',
  };

  const [segmento, setSegmento] = useState(segmentoParaEdicao || segmentoInicial);
  const [mensagem, setMensagem] = useState('');
  const token = localStorage.getItem('token');

  const capturarValor = (e) => {
    setSegmento({ ...segmento, [e.target.name]: e.target.value });
  };

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  const cadastrarSegmento = async () => {
    const corpo = JSON.stringify(segmento);
    console.log('ENVIADO: ', headers, ' ', corpo);

    try {
      const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + "/segmentos", {
        method: 'POST',
        body: corpo,
        headers: headers
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);
        setMensagem(responseData.message);
        if (response.status === 200) {
          limparFormulario();
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
      setMensagem("Erro ao cadastrar segmento.");
      return null;
    }
  };

  const limparFormulario = () => {
    setSegmento(segmentoInicial);
  };

  return (
    <form>
      <div className="form-row">
        <h1 className="mb-4 fw-normal">Cadastro de Segmento</h1>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="distancia">Distância</label>
          <input
            type="number"
            step="0.1"
            value={segmento.distancia}
            onChange={capturarValor}
            name="distancia"
            className="form-control"
            id="distancia"
            placeholder="Distância"
          />
        </div>
        <div className="form-group col-md-6">
          <label htmlFor="pontoInicial">Ponto Inicial</label>
          <input
            type="text"
            value={segmento.ponto_inicial}
            onChange={capturarValor}
            name="ponto_inicial"
            className="form-control"
            id="pontoInicial"
            placeholder="Ponto Inicial"
          />
        </div>
        <div className="form-group col-md-6">
          <label htmlFor="pontoFinal">Ponto Final</label>
          <input
            type="text"
            value={segmento.ponto_final}
            onChange={capturarValor}
            name="ponto_final"
            className="form-control"
            id="pontoFinal"
            placeholder="Ponto Final"
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="status">Status</label>
          <input
            type="number"
            value={segmento.status}
            onChange={capturarValor}
            name="status"
            className="form-control"
            id="status"
            placeholder="Status"
          />
        </div>
        <div className="form-group col-md-6">
          <label htmlFor="direcao">Direção</label>
          <input
            type="text"
            value={segmento.direcao}
            onChange={capturarValor}
            name="direcao"
            className="form-control"
            id="direcao"
            placeholder="Direção"
          />
        </div>
      </div>
      <div className="d-grid gap-2 d-md-flex justify-content-md-end">
        <input
          type="button"
          value="Cadastrar Segmento"
          onClick={cadastrarSegmento}
          className="btn btn-primary me-md-2"
        />
      </div>
      <div> {mensagem} </div>
    </form>
  );
};

export default FormularioSegmentos;