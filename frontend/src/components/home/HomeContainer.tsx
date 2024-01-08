import { useContext } from 'react';
import LatestProductsList from './LatestProductsList';
import styles from './home.module.css';
import { CategoriesContext } from '../../store/categories-context';

const HomeContainer = () => {
    const { categories } = useContext(CategoriesContext);

    return (
        <div className={styles['home-container']}>
            <LatestProductsList
                title='Latest Books'
                categoryId={categories.find(category => category.name === 'Books')?.id}
            />
            <LatestProductsList
                title='Latest Movies'
                categoryId={categories.find(category => category.name === 'Movies')?.id}
            />
            <LatestProductsList
                title='Latest Video Games'
                categoryId={categories.find(category => category.name === 'Video Games')?.id}
            />
        </div>
    )
}

export default HomeContainer;