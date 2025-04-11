import React, { useContext, useEffect, useState } from 'react';
import { UserContext } from '../../contexts/UserContext';
import { Payment } from '../../types/payment';
import { fetchPaymentHistory } from '../../services/payment';

const PaymentHistory: React.FC = () => {
    const { user } = useContext(UserContext);
    const [payments, setPayments] = useState<Payment[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const getPaymentHistory = async () => {
            try {
                const paymentHistory = await fetchPaymentHistory(user.id);
                setPayments(paymentHistory);
            } catch (err) {
                setError('Failed to fetch payment history');
            } finally {
                setLoading(false);
            }
        };

        if (user) {
            getPaymentHistory();
        }
    }, [user]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    return (
        <div>
            <h2>Payment History</h2>
            <ul>
                {payments.map(payment => (
                    <li key={payment.id}>
                        {payment.date}: ${payment.amount} - {payment.status}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PaymentHistory;