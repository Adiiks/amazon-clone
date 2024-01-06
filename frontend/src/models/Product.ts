export default interface Product {
    id: number,
    imageUrl: string,
    name: string,
    description: string,
    price: number,
    quantity: number,
    soldByUser: {
        id: number,
        fullName: string
    }
}