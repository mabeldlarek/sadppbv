import { useEffect, useState } from 'react';


function EdicaoUsuario ({ usuario, onSaveEdicao }) {
    const [obj, setObj] = useState(usuario || {
        email: '',
        nome: '',
        senha: '',
        registro: '',
        tipo_usuario: ''
      });
    
      const token = localStorage.getItem('token');
      const registro = localStorage.getItem('registro');

      const capturarValor = (e) => {
        setObj({ ...obj, [e.target.name]: e.target.value });
      }
    
      const salvar = () => {
        onSaveEdicao(obj);
      }    
  
    const editar = async () => {
        try {
            const response = await fetch("http://" + localStorage.getItem('ip') +":" + localStorage.getItem('porta') +"/usuarios/"+ obj.registro, {
              method: 'PUT',
              headers: {
                'Authorization': `Bearer ${token}`,
                'Content-type': 'application/json',
                'Accept': 'application/json',
                'Cache-Control': 'no-cache'
              },
              body: JSON.stringify(obj),
            });
      
            if (response.ok) {
              const data = await response.json();
              console.log(data);
              salvar();
            } else {
              console.error(`Erro na solicitação: ${response.status}`);
            }
          } catch (error) {
            console.error('Erro ao salvar edição:', error);
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
      </form>
    )
  }
  
  export default EdicaoUsuario;