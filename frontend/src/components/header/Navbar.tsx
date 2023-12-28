import { Link } from 'react-router-dom';
import styles from './header.module.css';
import Logo from './Logo';
import SearchBar from './SearchBar';
import { IoCartOutline } from "react-icons/io5";

const Navbar = () => {
    return (
        <div className={styles.navbar}>
            <div className={`${styles.logo} ${styles['nav-item']}`}>
                <Logo titleColor='white' arrowColor='white' />
            </div>
            <SearchBar />
            <div className={styles['user-menu']}>
                <Link to='/auth/sign-in' className={styles['nav-item']}>
                    <p>Hello, sign in</p>
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