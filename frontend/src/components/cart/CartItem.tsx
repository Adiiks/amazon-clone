import React from 'react';
import Product from '../../models/Product';
import styles from './cart.module.css';
import { Link } from 'react-router-dom';

type Props = {
    product: Product,
    quantitySelected: number,
    onRemoveItem: (productId: number) => void,
    onQuantityChange: (productId: number, newQuantity: number) => void
}

const CartItem: React.FC<Props> = ({ product, quantitySelected, onRemoveItem, onQuantityChange }) => {
    return (
        <div className={styles['cart-item-container']}>
            <div className={styles['img-container']}>
                <Link to={`/product-details/${product.id}`}>
                    <img src={product.imageUrl} alt={product.name} />
                </Link>
            </div>

            <div className={styles['item-middle-content']}>
                <Link to={`/product-details/${product.id}`}>
                    <p className={styles['item-name']}>{product.name}</p>
                </Link>
                {product.quantity >= quantitySelected ?
                    <span className={`${styles['stock-status']} ${styles['in-stock']}`}>In Stock</span> :
                    <span className={`${styles['stock-status']} ${styles['out-of-stock']}`}>Out of Stock</span>
                }
                <div className={styles['item-actions-container']}>
                    <div className={styles['quantity-input-wrapper']}>
                        <label htmlFor="cart-item-quantity">Qty:</label>
                        <input
                            type="number"
                            id='cart-item-quantity'
                            defaultValue={quantitySelected}
                            min={1}
                            max={product.quantity}
                            onChange={(event) => onQuantityChange(product.id, parseInt(event.target.value))}
                            onKeyDown={() => {return false}}
                        />
                    </div>
                    <span className={styles['item-action']}>On Stock: {product.quantity}</span>
                    <span
                        className={`${styles['item-action']} ${styles['delete-action']}`}
                        onClick={() => onRemoveItem(product.id)}
                    >
                        Delete
                    </span>
                </div>
            </div>

            <div className={styles['price-container']}>
                <span>${product.price.toFixed(2)}</span>
            </div>
        </div>
    )
}

export default CartItem;