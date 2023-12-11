import { useEffect, useState } from 'react';
import MD5 from "crypto-js/md5";

function EdicaoUsuario({ usuario, onSaveEdicao }) {
  const [obj, setObj] = useState(usuario || {
    email: '',
    nome: '',
    senha: '',
    registro: '',
    tipo_usuario: ''
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
    obj.senha = MD5(obj.senha).toString();
    console.log(obj.senha);
    const corpo = JSON.stringify(obj);
    console.log('ENVIADO: ', headers, " ", corpo);

    try {
      const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + "/usuarios/" + obj.registro, {
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
      setMensagem("Erro ao editar usuário.");
    }
  }

  return (
    <form>
      <div className="form-row">
        <h1 className="mb-4 fw-normal">Edição de Usuário</h1>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="nome">Nome</label>
          <input type="nome" value={obj.nome} onChange={capturarValor} name="nome" className="form-control" id="nome" placeholder="Nome" />
        </div>
        <div className="form-group col-md-6">
          <label htmlFor="inputEmail">Email</label>
          <input type="email" value={obj.email} onChange={capturarValor} name="email" className="form-control" id="inputEmail" placeholder="Email" />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="inputPassword">Senha</label>
          <input type="password" value={obj.senha}
            onChange={capturarValor}
            name="senha"
            className="form-control"
            id="inputPassword"
            placeholder="Password"
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group col-md-6">
          <label htmlFor="registro">Registro</label>
          <input type="text"
            value={obj.registro}
            name="registro"
            className="form-control"
            id="registro"
            placeholder="R.A"
            readOnly />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group col-md-4">
          <label htmlFor="inputTypeUser">Tipo de Usuário</label>
          <select id="inputTypeUser"
            value={obj.tipo_usuario}
            name="tipo_usuario"
            className="form-control"
            readOnly>
            <option selected>Tipo de Usuário</option>
            <option>0</option>
            <option>1</option>
          </select>
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

export default EdicaoUsuario;