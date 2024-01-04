import { useForm } from 'react-hook-form';
import CategoriesOptions from '../common/CategoriesOptions';
import styles from './product-creation.module.css';
import { useRef } from 'react';
import { backendUrl } from '../../environments';
import axios from 'axios';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router';

type FormValues = {
    name: string,
    description: string,
    price: number,
    quantity: number,
    image: FileList
}

const ProductCreation = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<FormValues>();
    const categoryRef = useRef<HTMLSelectElement | null>(null);
    const navigate = useNavigate();

    function onSubmit(data: FormValues) {
        const selectedOptionIdex = categoryRef.current?.options.selectedIndex;
        const selectedCategoryId = categoryRef.current?.options[selectedOptionIdex!].getAttribute('category-id');
        
        const payload = {
            name: data.name,
            description: data.description,
            price: data.price,
            quantity: data.quantity,
            categoryId: selectedCategoryId
        }

        const stringPayload = JSON.stringify(payload);
        const blobPayload = new Blob([stringPayload], {
            type: 'application/json'
        });

        const formData = new FormData();
        formData.append('image', data.image[0]);
        formData.append('request', blobPayload);

        const url = `${backendUrl}products`;

        axios.post(url, formData, {
            headers: {
                "Content-Type": 'multipart/form-data'
            }
        })
        .then(({ data }) => {
            navigate(`/product-details/${data}`);
        })
        .catch(() => {
            toast.error('Something went wrong !');
        });
    }

    return (
        <div className={styles['product-creation-container']}>
            <div className={styles['product-creation-form-wrapper']}>
                <h1>Create Product</h1>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className={styles['input-wrapper']}>
                        <label htmlFor="name">Product name</label>
                        <input
                            type="text"
                            id="name"
                            {...register('name', { required: 'Name is required' })}
                        />
                        <p className={styles.error}>{errors.name && errors.name.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="description">Product description</label>
                        <textarea
                            id="description"
                            {...register('description', { required: 'Description is required' })}
                        >
                        </textarea>
                        <p className={styles.error}>{errors.description && errors.description.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="price">Product price</label>
                        <input
                            type="number"
                            step=".01"
                            id='price'
                            {...register('price', {
                                required: 'Price is required',
                                min: {
                                    value: 1,
                                    message: 'Price must be at least 1'
                                }
                            })}
                        />
                        <p className={styles.error}>{errors.price && errors.price.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="quantity">How many products you want to sell ?</label>
                        <input
                            type="number"
                            id='quantity'
                            {...register('quantity', {
                                required: 'Quantity is required',
                                min: {
                                    value: 1,
                                    message: 'Quantity must be at least 1'
                                }
                            })}
                        />
                        <p className={styles.error}>{errors.quantity && errors.quantity.message}</p>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="category">Select category for your product</label>
                        <select
                            id="category"
                            ref={categoryRef}
                        >
                            <CategoriesOptions />
                        </select>
                    </div>

                    <div className={styles['input-wrapper']}>
                        <label htmlFor="image">Product image</label>
                        <input
                            type="file"
                            id='image'
                            accept='.png, .jpg'
                            {...register('image', { required: 'You have to select image for your product' })}
                        />
                        <p className={styles.error}>{errors.image && errors.image.message}</p>
                    </div>

                    <button>Create your product</button>
                </form>
            </div>
        </div>
    )
}

export default ProductCreation;