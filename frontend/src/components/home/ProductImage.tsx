import { Link } from 'react-router-dom';
import styles from './home.module.css';
import React from 'react';

type Props = {
    productId: number,
    imageUrl: string
}

const ProductImage: React.FC<Props> = ({ productId, imageUrl }) => {
    return (
        <div className={styles['product-image-wrapper']}>
            <Link to={`/product-details/${productId}`}>
                <img src={imageUrl} alt="product" />
            </Link>
        </div>
    )
}

export default ProductImage;