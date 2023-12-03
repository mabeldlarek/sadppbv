import React, { useState, useEffect } from 'react';


const EdicaoSegmentos = ({ segmento, onSaveEdicao }) => {
  const [obj, setObj] = useState({
    distancia: 0,
    ponto_inicial: "",
    ponto_final: "",
    status: 0,
    direcao: '',
  });

  const [listaPontos, setListaPontos] = useState([]);
  const token = localStorage.getItem('token');
  const [mensagem, setMensagem] = useState('');
  const [idAtualFin, setFin] = useState([]);
  const [idAtualIni, setIni] = useState([]);

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

  useEffect(() => {
    const obterSegmento = async () => {
      console.log('ENVIADO: ', headers);
      const metodo = 'GET';
      const caminho = "/segmentos/" + segmento;
      try {
        const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + caminho, {
          method: metodo,
          headers: headers
        });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
          const responseData = await response.json();
          console.log('RECEBIDO: ', responseData);
          if (response.status === 200) {
            setObj(responseData.segmento)
            setIni(responseData.segmento.ponto_inicial);
            setFin(responseData.segmento.ponto_final)
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

    const obterPontos = async () => {
      console.log('ENVIADO: ', headers);
      const metodo = 'GET';
      const caminho = "/pontos";
      try {
        const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + caminho, {
          method: metodo,
          headers: headers
        });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
          const responseData = await response.json();
          console.log('RECEBIDO: ', responseData);

          if (response.status === 200) {
            setListaPontos(responseData.pontos);
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
        setMensagem("Erro ao verificar conta do usuário logado.");
        return null;
      }
    };

  obterPontos();
  obterSegmento();

  },  []);


  const editar = async () => {
    const objToSend = {
      ...obj,
      ponto_inicial: parseInt(obj.ponto_inicial),
      ponto_final: parseInt(obj.ponto_final),
    };
  
    const corpo = JSON.stringify(objToSend);
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
      setMensagem("Erro ao editar segmento.");
    }
  }

  const getPontoIdByName = (listaPontos, pontoNome) => {
    const pontoEncontrado = listaPontos.find(ponto => ponto.nome === pontoNome);
    console.log("ENCONTRADO", pontoEncontrado.ponto_id);
    return pontoEncontrado ? pontoEncontrado.ponto_id : null;
  };

  return (
    <form>
       {console.log(obj)}
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
          <label htmlFor="ponto_inicial">Ponto Inicial</label>
          <p><strong>O atual é: {idAtualIni}</strong></p>

          <input
            type="number"
            value={obj.ponto_inicial}
            onChange={capturarValor}
            name="ponto_inicial"
            className="form-control"
            id="ponto_inicial"
            placeholder="Ponto Inicial (id)"
          />
        </div>
        <div className="form-group col-md-6">
          <label htmlFor="ponto_final">Ponto Final</label>
          <p><strong>O atual é: {idAtualFin}</strong></p>
          <input
            type="number"
            value={obj.ponto_final}
            onChange={capturarValor}
            name="ponto_final"
            className="form-control"
            id="ponto_final"
            placeholder="Ponto Final (id)"
          />
        </div>
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
      <div className="form-row">
          <h2 className="mb-4 fw-normal">Pontos Cadastrados</h2>
          <ul>
            {listaPontos.map((ponto) => (
              <li key={ponto.ponto_id}>{ponto.nome}, id: {ponto.ponto_id}</li>
            ))}
          </ul>
        </div>
    </form>

    
  )
}

export default EdicaoSegmentos;