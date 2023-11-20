import React, { useState } from 'react';
import Navigation from '../components/nav/Navigation';

function Home() {
    const [ip, setIp] = useState('');
    const [porta, setPorta] = useState('');
    const [mensagem, setMensagem] = useState('');

    const handleIPChange = (e) => {
        setIp(e.target.value);
    };
    
    const handlePortaChange = (e) => {
        setPorta(e.target.value);
    };

    const armazenarDadosConexao = () =>{
        localStorage.setItem('ip', ip);
        localStorage.setItem('porta', porta);
        console.log(localStorage.getItem('ip'));
        console.log(localStorage.getItem('porta'));
        const eventoAtualizacao = new Event('atualizacaoConexao');
        window.dispatchEvent(eventoAtualizacao);
        setMensagem("Dados informados e recursos liberados.");

    };

    return (
    <main  className="text-center align-items-center">
        <Navigation/>
        <div className="col-sm-25 text-left"> 
            <h1  className="mt-4">Bem vindo ao SADPPBV</h1>
            <p>O SADPPBV é o Sistema de auxílio para deslocamento a pé de pessoas com baixa visão.</p>
        </div>
        <div className="container p-5 mt-5">
        <div className="input-group input-group-sm mb-3 w-25">
            <div className="input-group-prepend">
                <span className="input-group-text" id="inputGroup-sizing-sm">Porta</span>
            </div>
            <input type="text" value={porta} onChange={handlePortaChange} className="porta" name="" id="porta" aria-label="Small" aria-describedby="inputGroup-sizing-sm"/>
        </div>
        <div className="input-group input-group-sm mb-3 w-25 ">
            <div className="input-group-prepend">
                <span className="input-group-text" id="inputGroup-sizing-sm">Endereço IP</span>
            </div>
            <input  type="text" value={ip} onChange={handleIPChange} className="ip" name="ip" id="ip" aria-label="Small" aria-describedby="inputGroup-sizing-sm"/>
        </div>
        <button aria-label="Small" aria-describedby="inputGroup-sizing-sm" onClick={armazenarDadosConexao}>Informar dados de conexão</button>
        </div>
        <div className="text-dark">{mensagem}</div>
    </main>
    );
  }

  export default Home;
