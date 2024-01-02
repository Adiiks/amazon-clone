import React from 'react';
import styles from './my-account.module.css';

type Props = {
    title: string,
    description: string,
    image: React.ReactNode,
    onClick: () => void
}

const AccountOption: React.FC<Props> = ({ title, description, image, onClick }) => {
    return (
        <div className={styles['account-option-box']} onClick={onClick}>
            {image}

            <div className={styles['text-wrapper']}>
                <h2>{title}</h2>
                <p>{description}</p>
            </div>
        </div>
    )
}

export default AccountOption;