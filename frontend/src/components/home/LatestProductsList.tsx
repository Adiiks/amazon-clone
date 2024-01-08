import React, { useEffect, useState } from 'react';
import styles from './home.module.css';
import Product from '../../models/Product';
import { backendUrl } from '../../environments';
import axios from 'axios';
import ProductImage from './ProductImage';

type Props = {
    title: string,
    categoryId: number | undefined
}

const DEFAULT_PAGE_NUMBER = 0;
const DEFAULT_PAGE_SIZE = 10;

const LatestProductsList: React.FC<Props> = ({ title, categoryId }) => {
    const [products, setProducts] = useState<Product[]>([]);

    useEffect(() => {
        if (!categoryId) return;

        const url = `${backendUrl}products/category/${categoryId}?page=${DEFAULT_PAGE_NUMBER}&size=${DEFAULT_PAGE_SIZE}`;

        axios.get(url)
            .then(({ data }) => {
                setProducts(data.content);
            });
    }, [categoryId]);

    return (
        <div className={styles['products-list-container']}>
            <h2>{title}</h2>
            <div className={styles['products-list']}>
                {products.map(product => 
                    <ProductImage 
                        key={product.id}
                        productId={product.id}
                        imageUrl={product.imageUrl}
                    />)}
            </div>
        </div>
    )
}

export default LatestProductsList;