import React from 'react';
import styles from './common.module.css';
import { extractNumberAfterDot, extractNumberBeforeDot } from '../../utils/PriceUtility';

type Props = {
    price: number
}

const ProductPrice: React.FC<Props> = ({ price }) => {
    return (
        <span className={styles['product-price-wrapper']}>
            <span className={styles['sub-price']}>$</span>
            <span className={styles['price']}>{extractNumberBeforeDot(price)}</span>
            <span className={styles['sub-price']}>{extractNumberAfterDot(price)}</span>
        </span>
    )
}

export default ProductPrice;