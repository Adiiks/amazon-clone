import { useEffect, useState } from "react";
import Category from "../../models/Category";
import { backendUrl } from "../../environments";
import axios from "axios";
import toast from "react-hot-toast";

const CategoriesOptions = () => {
    const [categories, setCategories] = useState<Category[]>([]);

    useEffect(() => {
        const url = `${backendUrl}categories`;

        axios.get(url)
            .then(({ data }) => {
                setCategories(data);
            })
            .catch(() => {
                toast.error('Something went wrong !');
            })
    }, []);

    return (
        <>
            {categories.map(category =>
                <option key={category.id} category-id={category.id}>{category.name}</option>
            )}
        </>
    )
}

export default CategoriesOptions;