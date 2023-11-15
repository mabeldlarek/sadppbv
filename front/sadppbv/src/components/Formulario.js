import { useEffect, useState } from 'react';

function Formulario({ usuarioParaEdicao }) {
  const person = {
    email: '',
    nome: '',
    senha: '',
    registro: '',
    tipo_usuario: ''
  }

  const token = localStorage.getItem('token');
  const [persons, setPersons] = useState([]);
  const [obj, setObjePerson] = useState(usuarioParaEdicao || person);
  const [selecao, setSelecao] = useState('opcao1');

  const handleChange = (event) => {
    setSelecao(event.target.value);
  };

  const capturarValor = (e) => {
    setObjePerson({...obj, [e.target.name]: e.target.value});
  }

  const cadastrar = () => {
    fetch("http://" + localStorage.getItem('ip') +":" + localStorage.getItem('porta') +"/usuarios", {
      method: 'POST',
      body: JSON.stringify(obj),
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-type': 'application/json',
        'Accept': 'application/json',
        'Cache-Control': 'no-cache'
      }
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.error(`Erro na requisição. Status: ${response.status}`);
          throw new Error('Erro na requisição');
        }
      })
      .then(retorno_convertido => {
        setPersons([...persons, retorno_convertido]);
        limparFormulario();
        console.log(retorno_convertido);
      })
      .catch(error => {
        console.error(error);
      });
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
    </form>
  )
}

export default Formulario;