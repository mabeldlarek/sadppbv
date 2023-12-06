import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const DetalhesRota = () => {

    const [rota, setRota] = useState([]);
    const [pontos, setPontos] = useState([]);
    const ip = localStorage.getItem('ip');
    const porta = localStorage.getItem('porta');
    const token = localStorage.getItem('token');
    const [mensagem, setMensagem] = useState('');
    const [dadosRota, setDadosRota] = useState({
        origem: '',
        destino: ''
    });
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-type': 'application/json',
        'Accept': 'application/json',
    };

    const capturarValor = (e) => {
        setDadosRota({ ...dadosRota, [e.target.name]: e.target.value });
    }

    useEffect(() => {
        console.log('ENVIADO: ', headers);
        const obterPontos = async () => {
            try {
                const response = await fetch(`http://${ip}:${porta}/pontos`, {
                    method: 'GET',
                    headers: headers
                });

                if (response.status === 200 || response.status === 401 || response.status === 403) {
                    const responseData = await response.json();
                    console.log('RECEBIDO: ', responseData);
                    if (response.status === 200) {
                        setPontos(responseData.pontos);
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
                setMensagem("Erro ao obter pontos.");
                return null;
            }
        };

        obterPontos();
    }, []);

    const obterRota = async () => {
        const corpo = JSON.stringify(dadosRota);
        console.log('ENVIADO:  ', headers);
        try {
            const response = await fetch(`http://${ip}:${porta}/rotas`, {
                method: 'POST',
                headers: headers,
                body: corpo
            });

            if (response.status === 200 || response.status === 401 || response.status === 403) {
                const responseData = await response.json();
                console.log('RECEBIDO: ', responseData);

                if (response.status === 200) {
                    setRota(responseData.rota);
                    setMensagem(responseData.message);
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
            setMensagem("Erro ao obter pontos.");
            return null;
        }
    };

    return (
        <div>
            <form>
                <div className="form-row">
                    <h1 className="mb-4 fw-normal">Informe o ponto de origem e destino desejados</h1>
                </div>
                <div className="form-row">
                    <div className="form-group col-md-6">
                        <label htmlFor="origem">Origem</label>
                        <select
                            value={dadosRota.origem}
                            onChange={capturarValor}
                            name="origem"
                            className="form-control"
                            id="origem"
                        >
                            <option value="">Selecione a Origem</option>
                            {pontos.map((ponto) => (
                                <option key={ponto.nome} value={ponto.nome}>
                                    {ponto.nome}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group col-md-6">
                        <label htmlFor="destino">Destino</label>
                        {/* Substituir o input pelo combobox */}
                        <select
                            value={dadosRota.destino}
                            onChange={capturarValor}
                            name="destino"
                            className="form-control"
                            id="destino"
                            defaultValue={dadosRota.destino}
                        >
                            <option value="">Selecione o Destino</option>
                            {/* Preencher as opções com os dados de pontos */}
                            {pontos.map((ponto) => (
                                <option key={ponto.nome} value={ponto.nome}>
                                    {ponto.nome}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                        <input type="button" value="Obter" onClick={obterRota} className="btn btn-primary me-md-2" />
                    </div>
                </div>
            </form>
            <div>
                <h2>Rota</h2>
                <ul className="list-group">
                    {rota.map((segmento, index) => (
                        <li key={segmento.segmento_id} className="list-group-item">
                            <div className="row">
                                <div className="col-md-3">
                                    <strong>Origem:</strong> {segmento.ponto_inicial}
                                </div>
                                <div className="col-md-3">
                                    <strong>Destino:</strong> {segmento.ponto_final}
                                </div>
                                <div className="col-md-3">
                                    <strong>Direção:</strong> {segmento.direcao}
                                </div>
                                <div className="col-md-3">
                                    <strong>Distância:</strong> {segmento.distancia}
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-md-12 text-end">
                                    <span className="badge bg-primary rounded-pill">{index + 1}</span>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
                <div> {mensagem} </div>
            </div>
        </div>
    );


}

export default DetalhesRota;