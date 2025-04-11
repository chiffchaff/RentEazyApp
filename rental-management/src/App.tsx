import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import ResetPassword from './components/auth/ResetPassword';
import OwnerDashboard from './components/dashboard/OwnerDashboard';
import TenantDashboard from './components/dashboard/TenantDashboard';
import CreateListing from './components/listings/CreateListing';
import ListingDetails from './components/listings/ListingDetails';
import PaymentForm from './components/payments/PaymentForm';
import PaymentHistory from './components/payments/PaymentHistory';

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/login" component={Login} />
                <Route path="/register" component={Register} />
                <Route path="/reset-password" component={ResetPassword} />
                <Route path="/owner-dashboard" component={OwnerDashboard} />
                <Route path="/tenant-dashboard" component={TenantDashboard} />
                <Route path="/create-listing" component={CreateListing} />
                <Route path="/listing/:id" component={ListingDetails} />
                <Route path="/payment" component={PaymentForm} />
                <Route path="/payment-history" component={PaymentHistory} />
            </Switch>
        </Router>
    );
};

export default App;