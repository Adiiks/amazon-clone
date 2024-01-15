import { useContext, useEffect, useState } from 'react';
import styles from './cart.module.css';
import { CartContext } from '../../store/cart-context';
import Product from '../../models/Product';
import { backendUrl } from '../../environments';
import axios from 'axios';
import toast from 'react-hot-toast';
import CartItem from './CartItem';

const Cart = () => {
    const cartContext = useContext(CartContext);
    const [products, setProducts] = useState<Product[]>([]);

    useEffect(() => {
        const productsIds: number[] = [];
        cartContext.items.forEach(cartItem => productsIds.push(cartItem.id));

        const url = `${backendUrl}products/ids`;

        if (productsIds.length === 0) return;

        axios.post(url, productsIds)
            .then(({ data }) => {
                setProducts(data);
            })
            .catch(() => {
                toast.error("Error occur during loading your cart");
            });
    }, [cartContext]);

    function handleDeleteItemFromCart(productId: number) {
        cartContext.removeFromCart(productId);
    }

    function handleQuantityChange(productId: number, newQuantity: number) {
        cartContext.updateItemQuantity(productId, newQuantity);
    }

    const totalItemsInCart = cartContext.getTotalItems();
    const totalCost = cartContext.getTotalCostOfItems();

    return (
        <div className={styles['container']}>
            <div className={styles['cart-container']}>
                {cartContext.items.length === 0 ?
                    <h1>Your Amazon Cart is empty.</h1> :
                    <>
                        <h1>Shopping Cart</h1>
                        <p className={styles['price-column-title']}>Price</p>
                        <hr />
                    </>
                }
                {products.map(product => {
                    return (
                        <div key={product.id} >
                            <CartItem
                                product={product}
                                quantitySelected={cartContext.items.find(item => item.id === product.id)?.quantity!}
                                onRemoveItem={handleDeleteItemFromCart}
                                onQuantityChange={handleQuantityChange}
                            />
                            <hr />
                        </div>
                    )
                })}
                {cartContext.items.length > 0 &&
                    <div className={styles['summary-container']}>
                        <p className={styles['summary']}>{`Subtotal (${totalItemsInCart} ${totalItemsInCart === 1 ? 'item' : 'items'}): `}
                            <span>
                                <strong>${totalCost.toFixed(2)}</strong>
                            </span>
                        </p>
                            <button className={styles['btn-proceed']}>Proceed to checkout</button>
                    </div>
                }
            </div>
        </div>
    )
}

export default Cart;