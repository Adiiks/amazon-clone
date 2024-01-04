import { useContext } from 'react';
import AccountOption from './AccountOption';
import styles from './my-account.module.css';
import { SlLogout } from "react-icons/sl";
import { MdPostAdd } from "react-icons/md";
import { AuthContext } from '../../store/auth-context';
import { useNavigate } from 'react-router';

const MyAccount = () => {
    
    const authContext = useContext(AuthContext);
    const navigate = useNavigate(); 

    function onLogout() {
        authContext.setToken(null, undefined);
        navigate('/');
    }

    return (
        <div className={styles['my-account-container']}>
            <div className={styles['my-account-inside-container']}>
                <h1>Your Account</h1>

                <div className={styles['account-options-container']}>
                    <AccountOption 
                            title='New Product'
                            description='Post a new product on sale'
                            image={<MdPostAdd size={55} />}
                            onClick={() => navigate('/post-product')}
                    />

                    <AccountOption 
                        title='Logout'
                        description='Logout from your account'
                        image={<SlLogout size={40} />}
                        onClick={onLogout}
                    />
                </div>
            </div>
        </div>
    )
}

export default MyAccount;