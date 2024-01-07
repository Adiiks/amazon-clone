import React, { createContext, useEffect, useState } from "react"
import Category from "../models/Category"
import { backendUrl } from "../environments"
import axios from "axios"
import toast from "react-hot-toast"

type CategoriesContextValue = {
    categories: Category[]
}

type CategoriesProviderProps = {
    children: React.ReactNode
}

export const CategoriesContext = createContext<CategoriesContextValue>({ categories: [] });

const CategoriesContextProvider: React.FC<CategoriesProviderProps> = ({ children }) => {
    const [categories, setCategories] = useState<Category[]>([]);

    useEffect(() => {
        const url = `${backendUrl}categories`;

        axios.get(url)
            .then(({ data }) => {
                setCategories(data);
            })
            .catch(() => {
                toast.error('Something went wrong !');
            });
    }, []);

    const contextValue: CategoriesContextValue = {
        categories: categories
    };

    return (
        <CategoriesContext.Provider value={contextValue}>
            {children}
        </CategoriesContext.Provider>
    )
}

export default CategoriesContextProvider;