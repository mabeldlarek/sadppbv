import React, { useState } from 'react';
import NavigationAdm from '../../components/nav/NavigationAdm';

function AdmLogado() {
    const [ip, setIp] = useState('');
    const [porta, setPorta] = useState('');
    const [mensagem, setMensagem] = useState('');

    return (
        <NavigationAdm/>
        
    );
  }

  export default AdmLogado;
