export function extractNumberBeforeDot(price: number) {
    const priceString = price + '';
    const dotIndex = priceString.indexOf('.');
    
    if (dotIndex >= 0) {
        return priceString.substring(0, dotIndex);
    } else {
        return priceString;
    }
}

export function extractNumberAfterDot(price: number) {
    const priceString = price + '';
    const dotIndex = priceString.indexOf('.');
    
    if (dotIndex >= 0) {
        return priceString.substring(dotIndex + 1, priceString.length);
    } else {
        return '00';
    }
}