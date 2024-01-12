import { useContext } from 'react';
import styles from './cart.module.css';
import { CartContext } from '../../store/cart-context';

const Cart = () => {
    const cartContext = useContext(CartContext);

    return(
        <div className={styles['container']}>
            <div className={styles['cart-container']}>
                {cartContext.items.length === 0 &&
                    <h1>Your Amazon Cart is empty.</h1>
                }
            </div>
        </div>
    )
}

export default Cart;