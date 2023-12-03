import React, { useState } from 'react';

const EdicaoPontos = ({ ponto, onSaveEdicao }) => {
  const [obj, setObj] = useState(ponto || {
    nome: '',
  });

  const token = localStorage.getItem('token');
  const [mensagem, setMensagem] = useState('');

  const capturarValor = (e) => {
    setObj({ ...obj, [e.target.name]: e.target.value });
  }

  const salvar = () => {
    console.log(obj);
    onSaveEdicao(obj);
  }

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  const editar = async () => {
    const corpo = JSON.stringify({
      nome: obj.nome,
    });

    console.log(ponto.id);

    try {
      const response = await fetch(`http://${localStorage.getItem('ip')}:${localStorage.getItem('porta')}/pontos/${ponto.ponto_id}`, {
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
      setMensagem("Erro ao editar ponto.");
    }
  }

  return (
    <form>
      <div className="form-row">
        <h1 className="mb-4 fw-normal">Edição de Ponto</h1>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="nome">Nome do Ponto</label>
          <input
            type="text"
            value={obj.nome}
            onChange={capturarValor}
            name="nome"
            className="form-control"
            id="nome"
            placeholder="Nome do Ponto"
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

export default EdicaoPontos;