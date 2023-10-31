




import { useEffect, useState } from 'react';

function Formulario({botao, eventoTecla, obj}){

  const person = {
    email: '',
    nome: '',
    senha: '',
    registro: '',
    tipo_usuario: ''
  }

  const [btnCadastrar, setBtnCadastrar] = useState(true)
  const [persons, setPersons] = useState([]);
  const [objPerson, setObjePerson] = useState(person);

  useEffect(()=>{
  
      fetch("http://localhost:" + 8080+ "/usuarios")
      .then(retorno => retorno.json())
      .then(retorno_convertido => {
        setPersons(retorno_convertido)
      console.log("Resposta da API:", retorno_convertido)
    });
  }, []);
  

  const aoDigitar = (e) => {
    setObjePerson({...objPerson, [e.target.name]:e.target.value});
    console.log(e.target)
  }

  const selecionar = (indice) => {
    setObjePerson(person[indice]);
    setBtnCadastrar(false);
  }

  const cadastrar = () => {
    fetch("http://localhost:8080/usuarios", {
      method:'post',
      body: JSON.stringify(objPerson),
      headers:{
        'Content-type':'application/json',
        'Accept':'application/json'
      }
    })
    .then(retorno => retorno)
    .then(retorno_convertido => {
      setPersons([...persons, retorno_convertido]);
      limparFormulario();
    })
  }

  const limparFormulario = () => {
    setObjePerson(person);
  }

    return(
  <form>
  <div className="form-row">
    <h1 className="mb-4 fw-normal">Cadastro de Usuário</h1>
  </div>
  <div className="form-row">
    <div className="form-group col-md-6">
      <label htmlFor="nome">Nome</label>
      <input type="nome" value={obj.nome} onChange={eventoTecla} name="nome" className="form-control" id="nome" placeholder="Nome"/>
    </div>
    <div className="form-group col-md-6">
      <label htmlFor="inputEmail4">Email</label>
      <input type="email" value={obj.email} onChange={eventoTecla} name="email" className="form-control" id="inputEmail4" placeholder="Email"/>
    </div>
  </div>
  <div className="form-row">
    <div className="form-group col-md-6">
      <label htmlFor="inputPassword">Senha</label>
      <input type="password" value={obj.senha} onChange={eventoTecla} name="senha" className="form-control" id="inputPassword" placeholder="Password"/>
    </div>
  </div>
  <div className="form-row">
    <div className="form-group col-md-6">
      <label htmlFor="registro">Registro</label>
      <input type="text" value={obj.registro} onChange={eventoTecla} name="registro" className="form-control" id="registro" placeholder="R.A"/>
    </div>
  </div>
  <div className="form-row">
    <div className="form-group col-md-4">
      <label htmlFor="inputTypeUser">Tipo de Usuário</label>
      <select id="inputTypeUser" value={obj.tipo_usuario} onChange={eventoTecla} name="tipo_usuario" className="form-control">
        <option selected>Tipo de Usuário</option>
        <option>0</option>
        <option>1</option>
      </select>
    </div>
  </div>
  <div className="d-grid gap-2 d-md-flex justify-content-md-end">
    {
      botao ?
      <input type="button" value='Cadastrar' onClick={cadastrar} className="btn btn-primary me-md-2"/>
      :
      <div>
        <input type="button" value='Alterar' className="btn btn-warning"/>
        <input type="button" value='Remover' className="btn btn-warning"/>
        <input type="button" value='Cancelar' className="btn btn-warning"/>
      </div>
    }
  </div>
</form>
    )
}

export default Formulario;