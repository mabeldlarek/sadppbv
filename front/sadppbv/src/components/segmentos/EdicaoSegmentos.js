import React, { useState } from 'react';

const EdicaoSegmentos = ({ segmento, onSaveEdicao }) => {
  const [obj, setObj] = useState(segmento || {
    distancia: 0,
    ponto_inicial: 0,
    ponto_final: 0,
    status: 0,
    direcao: '',
  });

  const token = localStorage.getItem('token');
  const [mensagem, setMensagem] = useState('');

  const capturarValor = (e) => {
    setObj({ ...obj, [e.target.name]: e.target.value });
  }

  const salvar = () => {
    onSaveEdicao(obj);
  }

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  const editar = async () => {
    const corpo = JSON.stringify(obj);
    console.log('ENVIADO: ', headers, " ", corpo);
    try {
      const response = await fetch(`http://${localStorage.getItem('ip')}:${localStorage.getItem('porta')}/segmentos/${obj.segmento_id}`, {
        method: 'PUT',
        headers: headers,
        body: corpo,
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);

        if (response.status === 200) {
          salvar();
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
      setMensagem("Erro ao editar segmento.");
    }
  }

  return (
    <form>
      <div className="form-row">
        <h1 className="mb-4 fw-normal">Edição de Segmento</h1>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="distancia">Distância</label>
          <input
            type="number"
            step="0.1"
            value={obj.distancia}
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
            type="number"
            value={obj.ponto_inicial}
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
            type="number"
            value={obj.ponto_final}
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
            value={obj.status}
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
            value={obj.direcao}
            onChange={capturarValor}
            name="direcao"
            className="form-control"
            id="direcao"
            placeholder="Direção"
          />
        </div>
      </div>
      <div className="d-grid gap-2 d-md-flex justify-content-md-end">
        {
          <input type="button" value='Editar' onClick={editar} className="btn btn-primary me-md-2" />
        }
      </div>
      <div> {mensagem} </div>
    </form>
  )
}

export default EdicaoSegmentos;