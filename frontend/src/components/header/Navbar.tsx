import styles from './header.module.css';
import logo from '../../assets/logo.svg';
import SearchBar from './SearchBar';
import { IoCartOutline } from "react-icons/io5";

const Navbar = () => {
    return (
        <div className={styles.navbar}>
            <div className={`${styles.logo} ${styles['nav-item']}`}>
                <img src={logo} alt="amazon logo" />
            </div>
            <SearchBar />
            <div className={styles['user-menu']}>
                <div className={styles['nav-item']}>
                    <p>Hello, sign in</p>
                    <p>Account</p>
                </div>
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