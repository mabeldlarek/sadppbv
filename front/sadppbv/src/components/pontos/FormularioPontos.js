import { useEffect, useState } from 'react';

function FormularioPontos({ usuarioParaEdicao }) {
    const pontoInicial = {
        nome: '',
    };

    const [ponto, setPonto] = useState(pontoInicial);
    const [mensagem, setMensagem] = useState('');
    const token = localStorage.getItem('token');

    const capturarValor = (e) => {
        setPonto({ ...ponto, [e.target.name]: e.target.value });
    };

    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-type': 'application/json',
        'Accept': 'application/json',
      };

    const cadastrarPonto = async () => {
        const corpo = JSON.stringify(ponto);
        console.log(corpo);

        try {
            const response = await fetch("http://" + localStorage.getItem('ip') + ":" + localStorage.getItem('porta') + "/pontos", {
                method: 'POST',
                body: corpo,
                headers: {
                    'Content-Type': 'application/json',
                },
            });

        if (response.status === 200 || response.status === 401 || response.status === 403) {
        const responseData = await response.json();
        console.log('RECEBIDO: ', responseData);
        setMensagem(responseData.message);
        if (response.status === 200) {
          limparFormulario();
        } else{
          setMensagem(responseData.message)
        }
      } else {
        console.error(`Erro na solicitação: ${response.status}`);
        setMensagem(`Erro na solicitação: ${response.status}`);

        const responseText = await response.text();
        console.log('Resposta completa:', responseText);
      }
    } catch (error) {
      console.error(error);
      setMensagem("Erro ao cadastrar ponto.");
      return null;
    }
    };

    const limparFormulario = () => {
        setPonto(pontoInicial);
    };

    return (
        <form>
            <div className="form-row">
                <h1 className="mb-4 fw-normal">Cadastro de Ponto</h1>
            </div>
            <div className="form-row">
                <div className="form-group col-md-6">
                    <label htmlFor="nome">Nome do Ponto</label>
                    <input
                        type="text"
                        value={ponto.nome}
                        onChange={capturarValor}
                        name="nome"
                        className="form-control"
                        id="nome"
                        placeholder="Nome do Ponto"
                    />
                </div>
            </div>
            <div className="d-grid gap-2 d-md-flex justify-content-md-end">
                <input
                    type="button"
                    value="Cadastrar Ponto"
                    onClick={cadastrarPonto}
                    className="btn btn-primary me-md-2"
                />
            </div>
            <div> {mensagem} </div>
        </form>
    );
};


export default FormularioPontos;