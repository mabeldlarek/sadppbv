import React, { useState } from 'react';
import md5 from 'md5'; 

function Login() {

    const login = {
        senha: '',
        registro: '',
    }

    const [registro, setRegistro] = useState('');
    const [senha, setSenha] = useState('');
    const [mensagem, setMensagem] = useState('');
    const [objLogin, setObjeLogin] = useState(login);

    const handleRegistroChange = (e) => {
        setRegistro(e.target.value);
    };
    
    const handleSenhaChange = (e) => {
        const senhaCriptografada = md5(e.target.value);
        setSenha(senhaCriptografada);
    };

    const realizarLogin = () => {
        const novoLogin = {
            registro: registro,
            senha: senha,
        };
        
        setObjeLogin(novoLogin);
        
        fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + "/login", {
            method: 'post',
            body: JSON.stringify(objLogin),
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
        .then((response) => {
            if (!response.ok) {
              return response.json();
            }
            return response.json();
          })
          .then((data) => {
            if (data.success === false) {
              const mensagemDeErro = data.message;
              setMensagem(mensagemDeErro);
              console.error('Mensagem de erro:', mensagemDeErro);
            } else {
              const token = data.token;
              console.log('Token:', token);
            }})
            .catch((error) => {
                console.error('Erro ao realizar o login:', error);
            });
    }

return (
    <section className="ftco-section">
        <div className="container">
            <div className="row justify-content-center">
                <form>
                    <h1 className="mb-4 fw-normal">Faça seu login no SADBBPV</h1>

                    <div className="form-floating">
                        <input type="text" value={registro} onChange={handleRegistroChange} className="form-control" name="registro" id="registro" aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        <label for="floatingInput">Registro</label>
                    </div>
                    <div className="form-floating">
                        <input type="text" value={senha} onChange={handleSenhaChange} className="form-control" name="senha" id="senha" aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        <label for="floatingsenha">Senha</label>
                    </div>
                    <button className="w-100 btn btn-lg btn-primary" type="submit" onClick={realizarLogin}>Logar</button>
                    <p className="mt-5 mb-3 text-muted">&copy; 2023</p>
                </form>
            </div>
            <div className="row justify-content-center">
                <div className="col-md-6 col-lg-5">
                    <div className="login-wrap p-4 p-md-5">
                        <div className="icon d-flex align-items-center justify-content-center">
                            <span className="fa fa-user-o"></span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </section>
)
}


export default Login;