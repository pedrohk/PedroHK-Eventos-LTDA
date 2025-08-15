// src/app/models/index.ts

export interface User {
    id: number;
    name: string;
    email: string;
}

export interface Product {
    id: number;
    title: string;
    price: number;
    description: string;
}

export interface Order {
    id: number;
    userId: number;
    productIds: number[];
    totalAmount: number;
}