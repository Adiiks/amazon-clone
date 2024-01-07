import { useContext } from "react";
import { CategoriesContext } from "../../store/categories-context";

const CategoriesOptions = () => {
    const { categories } = useContext(CategoriesContext);

    return (
        <>
            {categories.map(category =>
                <option key={category.id} category-id={category.id}>{category.name}</option>
            )}
        </>
    )
}

export default CategoriesOptions;