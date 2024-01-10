import { useEffect, useState } from 'react';
import styles from './product-details.module.css';
import Product from '../../models/Product';
import { useParams } from 'react-router';
import axios from 'axios';
import { backendUrl } from '../../environments';
import toast from 'react-hot-toast';
import ProductPrice from '../common/ProductPrice';

const ProductDetails = () => {
    const [product, setProduct] = useState<Product>();
    const { productId } = useParams();

    useEffect(() => {
        const url = `${backendUrl}products/${productId}`;

        axios.get(url)
            .then(({ data }) => {
                setProduct(data);
            })
            .catch(() => {
                toast.error('Something went wrong !');
            });
    }, []);

    const quantityOptions = [];
    for (let i = 1; i <= product?.quantity!; i++) {
        quantityOptions.push(
            <option key={i}>{i}</option>
        );
    }

    return (
        <div className={styles['product-details-container']}>
            <div className={styles['product-details-top-wrapper']}>

                {/* Product Image */}
                <div className={styles['product-image-wrapper']}>
                    <img src={product?.imageUrl} alt="product" />
                </div>

                {/* Product Basic Info */}
                <div className={styles['product-basic-info-section']}>
                    <h2>{product?.name}</h2>
                    <hr />
                    <ProductPrice price={product?.price!} />
                    <p>{product?.description}</p>
                </div>

                {/* Product Purchase */}
                <div className={styles['product-purchase-container']}>
                    <h3 className={product?.quantity! > 0 ? styles['title-in-stock'] : styles['title-out-of-stock']}>
                        {product?.quantity! > 0 ? 'In Stock' : 'Out of Stock'}
                    </h3>
                    
                    <div className={styles['quantity-selection']}>
                        <label htmlFor="quantity">Quantity: </label>
                        <select id="quantity">
                            {product?.quantity! > 0 ? quantityOptions : <option>0</option>}
                        </select>
                    </div>
                    
                    <button className={styles['add-to-cart-btn']}>Add to Cart</button>
                    <button className={styles['buy-now-btn']}>Buy Now</button>

                    <div className={styles['sold-by-info']}>
                        <p>Sold by</p>
                        <p>{product?.soldByUser.fullName}</p>
                    </div>

                    <button className={styles['add-to-list-btn']}>Add to List</button>
                </div>
            </div>
        </div>
    )
}

export default ProductDetails;