import { useEffect, useState } from 'react';
import styles from './header.module.css';
import Category from '../../models/Category';
import axios from 'axios';
import { backendUrl } from '../../environments';
import toast from 'react-hot-toast';
import { GoSearch } from "react-icons/go";

const SearchBar = () => {
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
        <form className={styles['search-form']}>
            <select name='category-selection' defaultValue={'All'}>
                <option>All</option>
                {categories.map(category => 
                        <option key={category.id}>{category.name}</option>
                    )}
            </select>
            <input type="text" name='search-input' placeholder='Search Amazon' />
            <button>
                <GoSearch size={20} />
            </button>
        </form>
    );
}

export default SearchBar;