import React from 'react';
import Product from '../../models/Product';
import styles from './search-products-result.module.css';
import { Link } from 'react-router-dom';
import ProductPrice from '../common/ProductPrice';

type Props = {
    product: Product
}

const ProductListItem: React.FC<Props> = ({ product }) => {
    return (
        <div className={styles['product-item-container']}>
            <div className={styles['image-container']}>
                <Link to={`/product-details/${product.id}`}>
                    <img src={product.imageUrl} alt={product.name} />
                </Link>
            </div>

            <div className={styles['product-details']}>
                <Link to={`/product-details/${product.id}`}>
                    <h3>{product.name}</h3>
                </Link>
                <ProductPrice price={product.price} />
            </div>
        </div>
    )
}

export default ProductListItem;