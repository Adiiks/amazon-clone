export function extractNumberBeforeDot(price: number) {
    const priceString = price + '';
    const dotIndex = priceString.indexOf('.');
    return priceString.substring(0, dotIndex);
}

export function extractNumberAfterDot(price: number) {
    const priceString = price + '';
    const dotIndex = priceString.indexOf('.');
    return priceString.substring(dotIndex + 1, priceString.length);
}