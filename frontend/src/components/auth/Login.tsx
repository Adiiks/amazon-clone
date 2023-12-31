import { Link, useNavigate } from 'react-router-dom';
import styles from './auth.module.css';
import { useForm } from 'react-hook-form';
import { useContext, useState } from 'react';
import { backendUrl } from '../../environments';
import axios from 'axios';
import toast from 'react-hot-toast';
import { AuthContext } from '../../store/auth-context';

type FormValues = {
    email: string,
    password: string
}

const Login = () => {
    const [loginError, setLoginError] = useState<boolean>(false);
    const { register, handleSubmit, formState: { errors } } = useForm<FormValues>();
    const navigate = useNavigate();
    const { setToken } = useContext(AuthContext);

    function onSubmit(data: FormValues) {
        const url = `${backendUrl}auth/login`;
        const body = {
            email: data.email,
            password: data.password
        };

        axios.post(url, body)
        .then(({ data }) => {
            setToken(data.token, data.expiresIn)
            navigate('/');
        })
            .catch(({ response }) => {
                if (response.status === 401) {
                    setLoginError(true);
                } else {
                    toast.error('Something went wrong !');
                }
            });
    }

    return (
        <div className={styles['auth-container']}>
            {loginError &&
                <div className={styles['login-error-box']}>
                    <h4>There was a problem</h4>
                    <p>Wrong email address or password</p>
                </div>
            }

            <div className={styles['auth-card']}>
                <h1>Sign in</h1>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={styles['input-wrapper']}>
                        <label htmlFor="email">Email</label>
                        <input
                            type="text"
                            id='email'
                            className={errors.email && styles['input-error']}
                            {...register('email', { required: 'Enter your email' })}
                        />
                        <p className={styles.error}>{errors.email && errors.email.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id='password'
                            className={errors.password && styles['input-error']}
                            {...register('password', { required: 'Enter your password' })}
                        />
                        <p className={styles.error}>{errors.password && errors.password.message}</p>
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