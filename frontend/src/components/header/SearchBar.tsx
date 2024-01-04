import styles from './header.module.css';
import { GoSearch } from "react-icons/go";
import CategoriesOptions from '../common/CategoriesOptions';

const SearchBar = () => {
    return (
        <form className={styles['search-form']}>
            <select name='category-selection' defaultValue={'All'}>
                <option>All</option>
                <CategoriesOptions />
            </select>
            <input type="text" name='search-input' placeholder='Search Amazon' />
            <button>
                <GoSearch size={20} />
            </button>
        </form>
    );
}

export default SearchBar;