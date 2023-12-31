import React, { useState, useEffect, useMemo} from 'react';

const EdicaoSegmentos = ({ segmento, onSaveEdicao }) => {
  const [dadosCarregados, setDadosCarregados] = useState(false);
  const [obj, setObj] = useState({
    distancia: 0,
    ponto_inicial: 0,
    ponto_final: 0,
    status: 0,
    direcao: '',
  });
  const [pontos, setListaPontos] = useState([]);
  const token = localStorage.getItem('token');
  const [mensagem, setMensagem] = useState('');

  const capturarValor = (e) => {
    const { value, type, checked } = e.target;
    const valor = type === 'checkbox' ? (checked ? 1 : 0) : value;

    setObj(prevState => ({
      ...prevState,
      [e.target.name]: valor,
    }));
  };

  const salvar = () => {
    onSaveEdicao(obj);
  }

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  useEffect(() => {
    const fetchData = async () => {
      try {

        await obterPontos();
        await obterSegmento();
        setDadosCarregados(true);
      } catch (error) {
        console.error("Erro ao carregar dados:", error);
        setMensagem("Erro ao carregar dados.");
      }
    };
  
    fetchData();
  }, [dadosCarregados]);

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
          const segmentoRecebido = responseData.segmento;

         const pontoInicial = pontos.find(ponto => ponto.nome === segmentoRecebido.ponto_inicial);
         const pontoFinal = pontos.find(ponto => ponto.nome === segmentoRecebido.ponto_final);
    
          if (pontoInicial && pontoFinal) {
            setObj({
              ...segmentoRecebido,
              ponto_inicial: pontoInicial ? pontoInicial.ponto_id : "",
              ponto_final: pontoFinal ? pontoFinal.ponto_id : "",
            });
            console.log('dados:', obj)
            setDadosCarregados(true);

          }


        console.log("terminado...")

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

  if (!dadosCarregados) {
    return <p>Carregando...{obj.ponto_final}</p>;
  } else {
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
            <label htmlFor="origem">Origem</label>
            <select
              value={obj.ponto_inicial}
              onChange={capturarValor}
              name="ponto_inicial"
              className="form-control"
              id="ponto_inicial"
            >
              <option value="">Selecione a Origem</option>
              {pontos.map((ponto) => (
                <option key={ponto.ponto_id} value={ponto.ponto_id}>
                  {ponto.nome}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group col-md-6">
            <label htmlFor="destino">Destino</label>
            <select
              value={obj.ponto_final}
              onChange={capturarValor}
              name="ponto_final"
              className="form-control"
              id="ponto_final"
            >
              <option value="">Selecione o Destino</option>
              {pontos.map((ponto) => (
                <option key={ponto.ponto_id} value={ponto.ponto_id}>
                  {ponto.nome}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group col-md-6 my-3">
            <div class="form-check">
              <input
                type="checkbox"
                id="status"
                name="status"
                className="form-check-input"
                onChange={capturarValor}
                checked={obj.status === 1}
              />
              <label className="custom-control-label" htmlFor="status">
                Está livre?
              </label>
            </div>
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
}

export default EdicaoSegmentos;