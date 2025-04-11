export interface Listing {
    id: string;
    ownerId: string;
    title: string;
    location: string;
    rentAmount: number;
    amenities: string[];
    photos: string[];
    createdAt: Date;
    updatedAt: Date;
}

export interface ListingDetails extends Listing {
    additionalExpenses: number[];
    paymentHistory: PaymentHistory[];
}

export interface PaymentHistory {
    tenantId: string;
    amount: number;
    date: Date;
    status: 'paid' | 'overdue';
}