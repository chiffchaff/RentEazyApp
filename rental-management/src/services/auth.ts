import axios from 'axios';
import { AuthResponse, User } from '../types/auth';
import { API_URL } from '../utils/constants';

export const login = async (email: string, password: string): Promise<AuthResponse> => {
    const response = await axios.post(`${API_URL}/auth/login`, { email, password });
    return response.data;
};

export const register = async (user: User): Promise<AuthResponse> => {
    const response = await axios.post(`${API_URL}/auth/register`, user);
    return response.data;
};

export const resetPassword = async (email: string): Promise<void> => {
    await axios.post(`${API_URL}/auth/reset-password`, { email });
};