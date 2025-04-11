export interface User {
    id: string;
    name: string;
    email: string;
    phoneNumber: string;
    role: 'owner' | 'tenant';
}

export interface AuthResponse {
    token: string;
    user: User;
}