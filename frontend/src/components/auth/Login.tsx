import { Link } from 'react-router-dom';
import styles from './auth.module.css';

const Login = () => {
    return (
        <div className={styles['auth-container']}>
            <div className={styles['auth-card']}>
                <h1>Sign in</h1>

                <form>
                    <div className={styles['input-wrapper']}>
                        <label htmlFor="email">Email</label>
                        <input type="text" id='email' />
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="password">Password</label>
                        <input type="password" id='password' />
                    </div>

                    <hr />
                    <button type='submit'>Continue</button>
                </form>
            </div>

            <div className={styles['link-divider']}>
                <div className={styles.line}></div>
                <h5>New to Amazon?</h5>
                <div className={styles.line}></div>
            </div>

            <div className={styles['link-wrapper']}>
                <Link to='/auth/sign-up'>Create your Amazon account</Link>
            </div>
        </div>
    )
}

export default Login;