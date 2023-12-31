import { Link } from 'react-router-dom';
import styles from './header.module.css';
import Logo from './Logo';
import SearchBar from './SearchBar';
import { IoCartOutline } from "react-icons/io5";
import { useContext, useEffect, useState } from 'react';
import User from '../../models/User';
import { AuthContext } from '../../store/auth-context';
import { backendUrl } from '../../environments';
import axios from 'axios';

const Navbar = () => {
    const [user, setUser] = useState<User>();
    const authContext = useContext(AuthContext);

    useEffect(() => {
        if (authContext.token) {
            const url = `${backendUrl}users/user`;

            axios.get(url, {headers: {
                Authorization: 'Bearer ' + authContext.token
            }})
                .then(({ data }) => {
                    setUser(data);
                });
        } else {
            setUser(undefined);
        }
    }, [authContext]);

    let userFirstName = '';
    if (user) {
        const whitespaceIndex = user.fullName.indexOf(' ');
        userFirstName = (whitespaceIndex < 0) ? user.fullName : user.fullName.slice(0, whitespaceIndex);
    }

    return (
        <div className={styles.navbar}>
            <div className={`${styles.logo} ${styles['nav-item']}`}>
                <Logo titleColor='white' arrowColor='white' />
            </div>
            <SearchBar />
            <div className={styles['user-menu']}>
                <Link to={user ? '/your-account' : '/auth/sign-in'} className={styles['nav-item']}>
                    <p>Hello, {user ? userFirstName : 'sign in'}</p>
                    <p>Account</p>
                </Link>
                <div className={styles['nav-item']}>
                    <p>Returns</p>
                    <p>& Orders</p>
                </div>
                <div className={styles.cart}>
                    <IoCartOutline size={25} />
                    <p>0</p>
                </div>
            </div>
        </div>
    );
}

export default Navbar;