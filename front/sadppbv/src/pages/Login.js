import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navigation from '../components/nav/Navigation';
import MD5 from "crypto-js/md5";


function Login() {
    const navigate = useNavigate();

    const login = {
        senha: '',
        registro: '',
    }

    const [objLogin, setObjeLogin] = useState(login);
    const [mensagem, setMensagem] = useState('');
    const ip = localStorage.getItem('ip');
    const porta = localStorage.getItem('porta');

    const headers = {
        'Content-type': 'application/json',
        'Accept': 'application/json'
    };

    const capturarValor = (e) => {
        setObjeLogin({ ...objLogin, [e.target.name]: e.target.value });
    }

    const realizarLogin = async () => {
        objLogin.senha = MD5(objLogin.senha).toString();
        const corpo = JSON.stringify(objLogin);
        console.log('ENVIADO: ', headers, ' ', corpo);
        try {
            const response = await fetch("http://" + ip + ":" + porta + "/login", {
                method: 'post',
                body: corpo,
                headers:headers
            });

            if (response.status === 200 || response.status === 401 || response.status === 403) {

                const responseData = await response.json();
                console.log('RECEBIDO: ', responseData);
                if (response.status === 200) {
                    localStorage.setItem('token', responseData.token);
                    localStorage.setItem('registro', responseData.registro);
                    redirecionarParaInicio();
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
            setMensagem("Erro ao verificar usuário.");
        }
    }

    const redirecionarParaInicio = () => {
        navigate("/redirect/" + objLogin.registro);
    };

    return (
        <section className="ftco-section">
            <Navigation />
            <div className="container">
                <div className="row justify-content-center">
                    {
                        <form>
                            <h1 className="mb-4 fw-normal">Faça seu login no SADBBPV</h1>
                            <div className="form-floating">
                                <input type="text" value={objLogin.registro} onChange={capturarValor} className="form-control form-control-sm" name="registro" id="registro" aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                                <label htmlFor="registro">Registro</label>
                            </div>
                            <div className="form-floating">
                                <input type="password" value={objLogin.senha} onChange={capturarValor} className="form-control form-control-sm" name="senha" id="senha" aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                                <label type="password" htmlFor="senha">Senha</label>
                            </div>
                            <button className="w-100 btn btn-lg btn-primary" type="button" onClick={realizarLogin}>Logar</button>
                            <p className="mt-5 mb-3 text-muted">&copy; 2023</p>
                        </form>
                    }
                </div>
                <div> {mensagem} </div>
            </div>
        </section>
    )
}


export default Login;