import { Link } from 'react-router-dom';
import styles from './auth.module.css';

const Registration = () => {
    return (
        <div className={styles['auth-container']}>
            <h1>Create account</h1>

            <form>
                <div className={styles['input-wrapper']}>
                    <label htmlFor="name">Your name</label>
                    <input type="text" id='name' placeholder='First and last name' />
                </div>

                <div className={styles['input-wrapper']}>
                    <label htmlFor="email">Email</label>
                    <input type='email' id='email' />
                </div>

                <div className={styles['input-wrapper']}>
                    <label htmlFor="password">Password</label>
                    <input type="password" id='password' placeholder='At least 6 characters' />
                </div>

                <div className={styles['input-wrapper']}>
                    <label htmlFor="re-enter-password">Re-enter password</label>
                    <input type="password" id='re-enter-password' />
                </div>

                <button type='button'>Continue</button>
            </form>

            <hr />
            <p>
                Already have an account? <Link to='/auth/sign-in'>Sign in</Link> 
            </p>
        </div>
    )
}

export default Registration;