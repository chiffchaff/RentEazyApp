export interface Payment {
    id: string;
    tenantId: string;
    amount: number;
    dueDate: string;
    status: 'paid' | 'overdue' | 'pending';
    createdAt: string;
}

export interface PaymentHistory {
    tenantId: string;
    payments: Payment[];
}