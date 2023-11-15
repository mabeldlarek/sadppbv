import React, { useState } from 'react';
import NavigationUser from '../../components/nav/NavigationUser';

function UserLogado() {
    const [ip, setIp] = useState('');
    const [porta, setPorta] = useState('');
    const [mensagem, setMensagem] = useState('');

    return (
        <NavigationUser/>
    );
  }

  export default UserLogado;