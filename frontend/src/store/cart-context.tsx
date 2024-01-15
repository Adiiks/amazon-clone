import { createContext, useState } from "react"
import Product from "../models/Product"

type CartItem = {
    id: number,
    name: string,
    price: number,
    quantity: number
}

type CartContextValue = {
    items: CartItem[],
    getTotalItems: () => number,
    addToCart: (product: Product, quantity: number) => void,
    removeFromCart: (productId: number) => void,
    updateItemQuantity: (productId: number, newQuantity: number) => void,
    getTotalCostOfItems: () => number
}

type CartProviderProps = {
    children: React.ReactNode
}

function setInitialCart() {
    const itemsString = localStorage.getItem('cartItems');
    const items: CartItem[] = (itemsString) ? JSON.parse(itemsString) : [];
    return items;
}

function saveToLocalStorage(cartItems: CartItem[]) {
    const cartItemsString = JSON.stringify(cartItems);
    localStorage.setItem('cartItems', cartItemsString);
}

export const CartContext = createContext<CartContextValue>({ 
    items: [],
    getTotalItems: () => {return 0},
    addToCart: () => {},
    removeFromCart: () => {},
    updateItemQuantity: () => {},
    getTotalCostOfItems: () => {return 0}
 });

const CartContextProvider: React.FC<CartProviderProps> = ({ children }) => {
    const [cartItems, setCartItems] = useState<CartItem[]>(setInitialCart());

    function getTotalItems() {
        return cartItems.reduce((total, item) => total + item.quantity, 0);
    }

    function addToCart(product: Product, quantity: number) {
        let cartItem = cartItems.find(item => item.id === product.id);
        let updatedCartItems;

        if (cartItem) {
            cartItem.quantity += quantity;
            updatedCartItems = [...cartItems]
        } 
        else {
            cartItem = {
                id: product.id,
                name: product.name,
                price: product.price,
                quantity
            }

            updatedCartItems = [...cartItems, cartItem];
        }

        saveToLocalStorage(updatedCartItems);
        setCartItems(updatedCartItems);
    }

    function removeFromCart(productId: number) {
        const updatedCartItems = cartItems.filter(item => item.id !== productId);
        
        setCartItems(updatedCartItems);
        saveToLocalStorage(updatedCartItems);
    }

    function updateItemQuantity(productId: number, newQuantity: number) {
        setCartItems(prevCartItems => {
            return [...prevCartItems].map(item => {
                if (item.id === productId) {
                    return {
                        ...item,
                        quantity: newQuantity
                    };
                }
                else return item;
            })
        });
    }

    function getTotalCostOfItems() {
        return cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
    }

    const contextValue: CartContextValue = {
        items: cartItems,
        getTotalItems,
        addToCart,
        removeFromCart,
        updateItemQuantity,
        getTotalCostOfItems
    };

    return (
        <CartContext.Provider value={contextValue}>
            {children}
        </CartContext.Provider>
    )
}

export default CartContextProvider;