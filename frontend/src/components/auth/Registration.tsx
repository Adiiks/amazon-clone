import { Link, useNavigate } from 'react-router-dom';
import styles from './auth.module.css';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { backendUrl } from '../../environments';
import toast from 'react-hot-toast';

type FormValues = {
    name: string,
    email: string,
    password: string,
    reEnteredPassword: string
}

const Registration = () => {
    const { register, handleSubmit, setError, formState: { errors } } = useForm<FormValues>();
    const navigate = useNavigate();

    function onSubmit(data: FormValues) {
        const url = `${backendUrl}auth/registration`;
        const payload = {
            fullName: data.name,
            email: data.email,
            password: data.password
        }

        axios.post(url, payload)
            .then(() => {
                navigate('/auth/sign-in');
            })
            .catch(({ response }) => {
                if (response.status === 409) {
                    setError('email', {
                        type: 'custom',
                        message: 'There\'s already an account with this email.'
                    });
                } else {
                    toast.error('Something went wrong !');
                }
            });
    }

    return (
        <div className={styles['auth-container']}>
            <div className={styles['auth-card']}>
                <h1>Create account</h1>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={styles['input-wrapper']}>
                        <label htmlFor="name">Your name</label>
                        <input
                            type="text"
                            id='name'
                            className={errors.name && styles['input-error']}
                            placeholder='First and last name'
                            {...register('name', { required: 'Enter your name' })}
                        />
                        <p className={styles.error}>{errors.name && errors.name.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="email">Email</label>
                        <input
                            type='text'
                            id='email'
                            className={errors.email && styles['input-error']}
                            {...register('email', {
                                required: 'Enter your email',
                                pattern: {
                                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                                    message: 'Invalid email address. Please correct and try again.'
                                }
                            })}
                        />
                        <p className={styles.error}>{errors.email && errors.email.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            id='password'
                            placeholder='At least 6 characters'
                            className={errors.password && styles['input-error']}
                            {...register('password', {
                                required: 'Minimum 6 characters required',
                                minLength: {
                                    value: 6,
                                    message: 'Minimum 6 characters required'
                                }
                            })}
                        />
                        <p className={styles.error}>{errors.password && errors.password.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="re-enter-password">Re-enter password</label>
                        <input
                            type="password"
                            id='re-enter-password'
                            className={errors.reEnteredPassword && styles['input-error']}
                            {...register('reEnteredPassword', {
                                validate: (value, formValues) => value === formValues.password || 'Passwords must match'
                            })}
                        />
                        <p className={styles.error}>{errors.reEnteredPassword && errors.reEnteredPassword.message}</p>
                    </div>

                    <button type='submit'>Continue</button>
                </form>

                <hr />
                <p>
                    Already have an account? <Link to='/auth/sign-in'>Sign in</Link>
                </p>
            </div>
        </div>
    )
}

export default Registration;