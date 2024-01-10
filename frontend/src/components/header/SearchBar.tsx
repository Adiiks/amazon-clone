import styles from './header.module.css';
import { GoSearch } from "react-icons/go";
import CategoriesOptions from '../common/CategoriesOptions';
import { useForm } from 'react-hook-form';
import { useRef } from 'react';
import { useNavigate } from 'react-router';

type FormValues = {
    searchValue: string
}

const SearchBar = () => {
    const { register, handleSubmit, reset} = useForm<FormValues>();
    const categoryRef = useRef<HTMLSelectElement | null>(null);
    const navigate = useNavigate();

    function onSubmit(data: FormValues) {
        if (!data.searchValue || data.searchValue.trim() === '') {
            reset();
            return;
        }

        const selectedOptionIdex = categoryRef.current?.options.selectedIndex;
        const selectedCategoryId = categoryRef.current?.options[selectedOptionIdex!].getAttribute('category-id');

        let queryParams = `?search=${data.searchValue}`;
        if (selectedCategoryId) {
            queryParams += `&categoryId=${selectedCategoryId}`;
        }

        navigate({
            pathname: '/search/products',
            search: queryParams
        });
    }

    return (
        <form className={styles['search-form']} onSubmit={handleSubmit(onSubmit)}>
            <select name='category-selection' defaultValue={'All'} ref={categoryRef}>
                <option>All</option>
                <CategoriesOptions />
            </select>
            <input type="text" id='search-input' placeholder='Search Amazon' {...register('searchValue')} />
            <button>
                <GoSearch size={20} />
            </button>
        </form>
    );
}

export default SearchBar;