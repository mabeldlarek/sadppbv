import React, { useState } from 'react';

function Home() {
    const [ip, setIp] = useState('');
    const [porta, setPorta] = useState('');
    const [mensagem, setMensagem] = useState('');

    var ipServidor;
    var portaServidor;

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
    };

    function verificarConexaoComServidor() {
        localStorage.clear();
        const urlDoServidor = 'http://' + ipServidor + ':' + portaServidor ; 
        fetch(urlDoServidor, {
          method: 'HEAD', 
          mode: 'no-cors', 
        })
          .then((response) => {
            if (response.ok) {
              armazenarDadosConexao();
              setMensagem("Conexão com o servidor está funcionando.");
            } else {
                setMensagem('Falha na conexão com o servidor.')
              console.log('Falha na conexão com o servidor.');
            }
          })
          .catch((error) => {
            setMensagem('Falha na conexão com o servidor.')
            console.error('Erro ao verificar a conexão com o servidor:', error);
          });
      }

    return (
    <main  className="text-center align-items-center">
        <div className="col-sm-25 text-left"> 
            <h1  className="mt-4">Bem vindo ao SADPPBV</h1>
            <p>O SADPPBV é o Sistema de auxílio para deslocamento a pé de pessoas com baixa visão.</p>
        </div>
        <div class="container p-5 mt-5">
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
        <button aria-label="Small" aria-describedby="inputGroup-sizing-sm" onClick={verificarConexaoComServidor}>Verificar conexão</button>
        </div>
        <div className="text-dark">{mensagem}</div>
   
    </main>
    );
  }

  export default Home;
