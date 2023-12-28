import { Outlet } from 'react-router';
import Logo from '../components/header/Logo';
import styles from './auth-page.module.css';

const AuthPage = () => {
    return (
        <main className={styles['auth-page']}>
            <Logo titleColor='black' arrowColor='orange' />
            <Outlet />
        </main>
    );
}

export default AuthPage;