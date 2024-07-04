import { Url } from '../../GlobalVal'
import { ApiKey } from '@env';

export default AuthPost = async () => {
    const respotaPost = await fetch(`${Url}/Login/Autenticar?token=${ApiKey}`, {
        method: 'POST'
    });
    if (respotaPost.status === 200) return await respotaPost.json();
    return false
};