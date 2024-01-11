import { createContext, useState } from "react"

type CartItem = {
    id: number,
    name: string,
    price: number,
    quantity: number
}

type CartContextValue = {
    items: CartItem[],
    getTotalItems: () => number
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
    getTotalItems: () => {return 0}
 });

const CartContextProvider: React.FC<CartProviderProps> = ({ children }) => {
    const [cartItems, setCartItems] = useState<CartItem[]>(setInitialCart());

    function getTotalItems() {
        return cartItems.reduce((total, item) => total + item.quantity, 0);
    }

    const contextValue: CartContextValue = {
        items: cartItems,
        getTotalItems
    };

    return (
        <CartContext.Provider value={contextValue}>
            {children}
        </CartContext.Provider>
    )
}

export default CartContextProvider;