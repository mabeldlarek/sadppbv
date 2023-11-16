import { useEffect, useState } from 'react';
import MD5 from "crypto-js/md5";

function Formulario({ usuarioParaEdicao }) {
  const person = {
    email: '',
    nome: '',
    senha: '',
    registro: '',
    tipo_usuario: 0
  }

  const token = localStorage.getItem('token');
  const [obj, setObjePerson] = useState(usuarioParaEdicao || person);
  const [mensagem, setMensagem] = useState('');

  const capturarValor = (e) => {
    setObjePerson({ ...obj, [e.target.name]: e.target.value });
  }

  const headers = {
    'Authorization': `Bearer ${token}`,
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  const cadastrar = async () => {
    obj.senha = MD5(obj.senha).toString();
    obj.tipo_usuario = parseInt(obj.tipo_usuario);
    const corpo = JSON.stringify(obj);

    console.log('ENVIADO: ', headers, ' ', corpo);

    try {
      const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + "/usuarios", {
        method: 'POST',
        body: corpo,
        headers: headers
      });

      if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);
        if (response.status === 200) {
          setMensagem(responseData.message);
          limparFormulario();
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
      setMensagem("Erro ao cadastrar usuário.");
      return null;
    }
}

const limparFormulario = () => {
  setObjePerson(person);
}

return (
  <form>
    <div className="form-row">
      <h1 className="mb-4 fw-normal">Cadastro de Usuário</h1>
    </div>
    <div className="form-row">
      <div className="form-group col-md-6">
        <label htmlFor="nome">Nome</label>
        <input type="nome" value={obj.nome} onChange={capturarValor} name="nome" className="form-control" id="nome" placeholder="Nome" />
      </div>
      <div className="form-group col-md-6">
        <label htmlFor="inputEmail4">Email</label>
        <input type="email" value={obj.email} onChange={capturarValor} name="email" className="form-control" id="inputEmail4" placeholder="Email" />
      </div>
    </div>
    <div className="form-row">
      <div className="form-group col-md-6">
        <label htmlFor="inputPassword">Senha</label>
        <input type="password" value={obj.senha} onChange={capturarValor} name="senha" className="form-control" id="inputPassword" placeholder="Password" />
      </div>
    </div>
    <div className="form-row">
      <div className="form-group col-md-6">
        <label htmlFor="registro">Registro</label>
        <input type="text" value={obj.registro} onChange={capturarValor} name="registro" className="form-control" id="registro" placeholder="R.A" />
      </div>
    </div>
    <div className="form-row">
      <div className="form-group col-md-4">
        <label htmlFor="inputTypeUser">Tipo de Usuário</label>
        <select id="inputTypeUser" value={obj.tipo_usuario} onChange={capturarValor} name="tipo_usuario" className="form-control">
          <option selected >Tipo de Usuário</option>
          <option>0</option>
          <option>1</option>
        </select>
      </div>
    </div>
    <div className="d-grid gap-2 d-md-flex justify-content-md-end">
      {
        <input type="button" value='Cadastrar' onClick={cadastrar} className="btn btn-primary me-md-2" />
      }
    </div>
    <div> {mensagem} </div>
  </form>
)
}

export default Formulario;