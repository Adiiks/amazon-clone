import { useSearchParams } from 'react-router-dom';
import styles from './search-products-result.module.css'
import Product from '../../models/Product';
import { useEffect, useState } from 'react';
import { backendUrl } from '../../environments';
import axios from 'axios';
import ProductListItem from './ProductListItem';
import ReactPaginate from 'react-paginate';

const PRODUCTS_PER_PAGE = 1;

const SearchProductsResult = () => {
    const [searchParams] = useSearchParams();
    const [products, setProducts] = useState<Product[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [totalProducts, setTotalProducts] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(1);

    const searchValue = searchParams.get('search');
    const categoryId = searchParams.get('categoryId');

    useEffect(() => {
        let url = '';
        if (categoryId) {
            url = `${backendUrl}products/category/${categoryId}?search=${searchValue}&size=${PRODUCTS_PER_PAGE}&page=${currentPage}`;
        } else {
            url = `${backendUrl}products?search=${searchValue}&size=${PRODUCTS_PER_PAGE}&page=${currentPage}`;
        }

        axios.get(url)
            .then(({ data }) => {
                setProducts(data.content);
                setTotalProducts(data.totalElements);
                setTotalPages(data.totalPages);
            });
    }, [currentPage, searchValue, categoryId]);

    const startIndex = (products.length === 0) ? 0 : currentPage * PRODUCTS_PER_PAGE + 1;
    const endIndex = (products.length === PRODUCTS_PER_PAGE) ? (currentPage + 1) * PRODUCTS_PER_PAGE : currentPage * PRODUCTS_PER_PAGE + products.length;

    return (
        <>
            <div className={styles['results-statistics-container']}>
                <p>
                    {startIndex} - {endIndex} of {totalProducts} results for
                    <span className={styles['search-value']}> "{searchValue}"</span>
                </p>
            </div>

            <div className={styles['results-container']}>
                <h2>Results</h2>

                <div className={styles['products-list']}>
                    {products.map(product =>
                        <ProductListItem key={product.id} product={product} />
                    )}
                </div>

                <div className={styles['pagination-container']}>
                    <ReactPaginate
                        initialPage={currentPage}
                        pageCount={totalPages}
                        pageRangeDisplayed={4}
                        marginPagesDisplayed={4}
                        previousLabel={'< Previous'}
                        nextLabel={'Next >'}
                        breakLabel={'...'}
                        containerClassName={styles.pagination}
                        pageClassName={styles['pagination-item']}
                        activeClassName={styles['active-page']}
                        previousClassName={styles['pagination-item']}
                        nextClassName={styles['pagination-item']}
                        onPageChange={({ selected }) => setCurrentPage(selected)}
                        renderOnZeroPageCount={null}
                    />
                </div>
            </div>
        </>
    )
}

export default SearchProductsResult;